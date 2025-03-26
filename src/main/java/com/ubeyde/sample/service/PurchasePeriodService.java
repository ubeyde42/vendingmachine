package com.ubeyde.sample.service;

import com.ubeyde.sample.event.PurchaseNotifyPeriodReachedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class PurchasePeriodService {


    @EventListener
    public void handlePurchaseNotifyPeriodReachedEvent(PurchaseNotifyPeriodReachedEvent event) {
        //if period reached, notify via email and start needed operations
        System.out.println("CONGRATS! MACHINE REACHED "+event.getPurchaseCount()+" PURCHASES");
    }
}
