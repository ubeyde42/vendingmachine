package com.ubeyde.sample.entity;

import com.ubeyde.sample.enums.MachineStatus;
import com.ubeyde.sample.exception.InsufficientBalanceException;
import com.ubeyde.sample.exception.ProductNotFoundException;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Product> products = new ArrayList<>();


    public Machine(Integer balanceTRY, MachineStatus status, Date lastUpdated) {
        this.balanceTRY = balanceTRY;
        this.status = status;
        this.lastUpdated = lastUpdated;
    }

    public @NotNull(message = "Price cannot be null") @Min(value = 0, message = "Price must be a positive number or zero") Integer purchaseProduct(Long productId) {

        if (status != MachineStatus.ACTIVE) {
            throw new IllegalStateException("Makine aktif değil, işlem yapılamaz.");
        }

        Product product = products.stream()
                .filter(p -> p.getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ProductNotFoundException("Ürün bulunamadı."));

        if (product.getStockQuantity() <= 0) {
            throw new IllegalStateException("Stokta yeterli ürün yok.");
        }

        int remainingBalance = balanceTRY-product.getPrice();
        if (remainingBalance < 0) {
            throw new InsufficientBalanceException(Math.abs(remainingBalance));
        }

        //if checks are ok, apply changes to stock and remaining balance
        product.decreaseStock();
        setBalanceTRY(remainingBalance);

        return product.getPrice();

    }
}
