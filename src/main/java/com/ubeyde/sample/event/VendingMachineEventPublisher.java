package com.ubeyde.sample.event;


import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
    public class VendingMachineEventPublisher {

    private final ApplicationEventPublisher eventPublisher;

    public VendingMachineEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    public void publishEvent(Object event) {
            eventPublisher.publishEvent(event);
        }
    }
