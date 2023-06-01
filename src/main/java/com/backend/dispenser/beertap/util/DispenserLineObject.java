package com.backend.dispenser.beertap.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DispenserLineObject {
    private String opened_at;
    private String closed_at;
    private double flow_volume;
    private double total_spent;
}
