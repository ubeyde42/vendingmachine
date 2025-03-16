package com.ubeyde.sample.service;

import com.ubeyde.sample.event.RefundRequestedEvent;
import com.ubeyde.sample.event.RefundSuccessEvent;
import com.ubeyde.sample.event.VendingMachineEventPublisher;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RefundService {

    private final VendingMachineEventPublisher eventPublisher;

    public RefundService(VendingMachineEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public boolean handleRefund() {

        RefundRequestedEvent refundRequestedEvent = new RefundRequestedEvent();
        eventPublisher.publishEvent(refundRequestedEvent);

        Random random = new Random();
        boolean refundSuccess = random.nextBoolean();
        // In real system,an external service implementation will be held here
        //boolean refundSuccess = externalRefundService.processRefund(refundAmount);

        if (refundSuccess) {
            RefundSuccessEvent refundSuccessEvent = new RefundSuccessEvent();
            eventPublisher.publishEvent(refundSuccessEvent);
            return true;
        } else {
            return false;
        }
    }
}
