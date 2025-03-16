package com.ubeyde.sample.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Product name cannot be blank")
    @Size(min = 3, max = 150, message = "Product name must be between 3 and 50 characters")
    private String name;

    @NotNull(message = "Price cannot be null")
    @Min(value = 0, message = "Price must be a positive number or zero")
    private Integer price;

    @Column(name = "stock_quantity")
    @PositiveOrZero(message = "Stock quantity must be zero or positive")
    private int stockQuantity;

    public Product(String name, Integer price) {
        this.name = name;
        this.price = price;
    }
}
