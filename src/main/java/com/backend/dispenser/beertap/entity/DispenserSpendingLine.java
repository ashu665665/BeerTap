package com.backend.dispenser.beertap.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;

@Entity
@Table(name = "DISPENSER_SPN_LINE")
@Data
@NoArgsConstructor
public class DispenserSpendingLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SPN_ID")
    private Long spn_id;

    @Column(name = "OPEN_AT")
    private String opened_at;

    @Column(name = "CLOSE_AT")
    private String closed_at;

    @Column(name = "TTL_SPENT")
    private double total_spent;

    @Column(name = "DISPENSER_ID")
    private String dispenser_id;

    public DispenserSpendingLine(String opened_at, String closed_at, double total_spent, String dispenser_id) {
        this.opened_at = opened_at;
        this.closed_at = closed_at;
        this.total_spent = total_spent;
        this.dispenser_id = dispenser_id;
    }
}
