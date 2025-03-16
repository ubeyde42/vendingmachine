package com.ubeyde.sample.event;

public class MoneyDepositedEvent {

    private final Integer amount;

    public MoneyDepositedEvent(Integer amount) {
        this.amount = amount;
    }

    public Integer getAmount() {
        return amount;
    }
}

