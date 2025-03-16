package com.ubeyde.sample.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
public class Deposit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Amount cannot be null")
    @Min(value = 0, message = "Amount must be a positive number or zero")
    private Integer amount;

    @NotNull(message = "Currency cannot be null")
    private String currency;

    private LocalDateTime timestamp;

    public Deposit(Integer amount, String currency) {
        this.amount = amount;
        this.currency = currency;
        this.timestamp = LocalDateTime.now();
    }

}
