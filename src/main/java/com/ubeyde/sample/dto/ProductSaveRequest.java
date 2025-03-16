package com.ubeyde.sample.dto;

import lombok.Data;

@Data
public class ProductSaveRequest {

    private String name;
    private Integer price;
    private int stockQuantity;

}
