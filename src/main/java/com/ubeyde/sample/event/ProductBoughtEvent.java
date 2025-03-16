package com.ubeyde.sample.event;


import lombok.Getter;

@Getter
public class ProductBoughtEvent {
    private final Long productId;
    private final Integer amountPaid;

    public ProductBoughtEvent(Long productId, Integer amountPaid) {
        this.productId = productId;
        this.amountPaid = amountPaid;
    }


}
