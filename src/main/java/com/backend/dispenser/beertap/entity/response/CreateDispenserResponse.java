package com.backend.dispenser.beertap.entity.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateDispenserResponse {
    private String id;
    private double flow_volume;
}
