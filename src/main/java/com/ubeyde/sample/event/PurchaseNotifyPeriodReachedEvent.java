package com.ubeyde.sample.event;

import lombok.Getter;

@Getter
public class PurchaseNotifyPeriodReachedEvent {

    private final long purchaseCount;

    public PurchaseNotifyPeriodReachedEvent(long purchaseCount) {
        this.purchaseCount = purchaseCount;
    }
}
