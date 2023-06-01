package com.backend.dispenser.beertap.entity.response;

import com.backend.dispenser.beertap.util.DispenserLineObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DispenserSpendingResponse {
    private double amount;
    private DispenserLineObject[] usages;
}
