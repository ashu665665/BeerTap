package com.backend.dispenser.beertap.entity.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DispenserStatusRequest {
    private String status;
    private String updated_at;
}
