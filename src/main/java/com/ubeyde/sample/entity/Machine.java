package com.ubeyde.sample.entity;

import com.ubeyde.sample.enums.MachineStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Machine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer balanceTRY = 0;

    private MachineStatus status = MachineStatus.ACTIVE;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdated = new Date();


    public Machine(Integer balanceTRY, MachineStatus status, Date lastUpdated) {
        this.balanceTRY = balanceTRY;
        this.status = status;
        this.lastUpdated = lastUpdated;
    }

}
