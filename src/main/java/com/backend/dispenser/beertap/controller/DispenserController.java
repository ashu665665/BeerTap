package com.backend.dispenser.beertap.controller;

import com.backend.dispenser.beertap.entity.Dispenser;
import com.backend.dispenser.beertap.entity.DispenserSpendingLine;
import com.backend.dispenser.beertap.entity.request.CreateDispenserRequest;
import com.backend.dispenser.beertap.entity.request.DispenserStatusRequest;
import com.backend.dispenser.beertap.entity.response.DispenserSpendingResponse;
import com.backend.dispenser.beertap.exception.CustomException;
import com.backend.dispenser.beertap.repository.DispenserSpendingLineRepository;
import com.backend.dispenser.beertap.util.*;
import com.backend.dispenser.beertap.entity.response.CreateDispenserResponse;
import com.backend.dispenser.beertap.repository.DispenserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Api(value = "Dispenser API's")
@RestController
@RequestMapping("/beer-tap-dispenser/dispenser")
public class DispenserController {

    @Autowired
    private DispenserRepository dispenserRepository;

    @Autowired
    private DispenserSpendingLineRepository spendingLineRepository;

    @Value("${charges.per.litre}")
    private double charge;

    //Create a new dispenser
    @ApiOperation(value = "Create a new dispenser")
    @PostMapping("")
    public ResponseEntity<?> createDispenser(@RequestBody CreateDispenserRequest request) {
        try{
            Double flow_volume = request.getFlow_volume();
            Dispenser createdDispenser = dispenserRepository.save(new Dispenser
                (UUIDGenerator.generateUUID(),DispenserStatus.close.toString(),flow_volume));
            CreateDispenserResponse response = new CreateDispenserResponse(
                createdDispenser.getId(),createdDispenser.getFlow_volume());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected API error");
        }
    }

    //Change the dispenser status for a given dispenser Id
    @ApiOperation(value = "Change the dispenser status for a given dispenser Id")
    @PutMapping("/{id}/status")
    public ResponseEntity<String> updateDispenserStatus(@PathVariable String id, @RequestBody DispenserStatusRequest request) {
        try{
            Optional<Dispenser> fetchDispenser = dispenserRepository.findById(id);
            if (fetchDispenser.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Requested dispenser does not exist");
            }
            if (fetchDispenser.get().getDispenserStatus().equalsIgnoreCase(request.getStatus())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Dispenser is already " + request.getStatus());
            }

            fetchDispenser.ifPresent(dispenser -> {
                dispenser.setDispenserStatus(request.getStatus());
                if (request.getStatus().equalsIgnoreCase("open")) {
                    spendingLineRepository.save(new DispenserSpendingLine(request.getUpdated_at(), null, 0, dispenser.getId()));
                } else if (request.getStatus().equalsIgnoreCase("close")) {
                    DispenserSpendingLine spendingLine = spendingLineRepository.findByDispenserIdAndClosedAt(dispenser.getId());
                    spendingLine.setClosed_at(request.getUpdated_at());
                    double amount = charge * dispenser.getFlow_volume() * DateUtil.datetimeDiffInSeconds(spendingLine.getOpened_at(), spendingLine.getClosed_at());
                    spendingLine.setTotal_spent(amount);
                    spendingLineRepository.save(spendingLine);
                }
            });
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Status of the tap changed correctly");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected API error");
        }
    }

    //Returns the money spent by the given dispenser Id
    @ApiOperation(value = "Returns the money spent by the given dispenser Id")
    @GetMapping("/{id}/spending")
    public ResponseEntity<?> money_spent(@PathVariable String id){
        try{
            Optional<Dispenser> fetchDispenser = dispenserRepository.findById(id);
            if (fetchDispenser.isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Requested dispenser does not exist");
            }

            List<DispenserLineObject> usages = spendingLineRepository.findAllByDispenserId(id).stream().map(line -> new DispenserLineObject(line.getOpened_at(),line.getClosed_at(),fetchDispenser.get().getFlow_volume(),line.getTotal_spent())).collect(
                Collectors.toList());
            double total_charges = usages.stream().mapToDouble(DispenserLineObject::getTotal_spent).sum();
            double total_ifopened = 0;

            if(fetchDispenser.get().getDispenserStatus().equalsIgnoreCase("open")){
                DispenserSpendingLine spendingLine = spendingLineRepository.findByDispenserIdAndClosedAt(
                    fetchDispenser.get().getId());
                total_ifopened = charge * fetchDispenser.get().getFlow_volume() * DateUtil.datetimeDiffInSeconds(spendingLine.getOpened_at(),
                    Instant.now().toString());
                usages.get(usages.size()-1).setTotal_spent(total_ifopened);
                total_charges = total_charges + total_ifopened;
            }
            DispenserSpendingResponse response = new DispenserSpendingResponse(total_charges,usages.toArray(new DispenserLineObject[0]));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unexpected API error");
        }

    }
}
