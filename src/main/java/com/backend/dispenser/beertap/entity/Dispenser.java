package com.backend.dispenser.beertap.entity;

import com.backend.dispenser.beertap.util.UUIDGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "DISPENSER")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Dispenser {

    @Id
    @Column(name = "DISPENSER_ID")
    private String id;

    @Column(name = "DISPENSER_STS")
    private String dispenserStatus;

    @Column(name = "FLOW_VOL")
    private double flow_volume;

}
