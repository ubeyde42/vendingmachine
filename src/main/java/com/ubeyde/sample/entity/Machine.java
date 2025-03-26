package com.ubeyde.sample.entity;

import com.ubeyde.sample.dto.ProductSaveRequest;
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

    public void addNewProduct(ProductSaveRequest newProductDto) {
        if (products.stream().filter(p -> p.getName().equals(newProductDto.getName())).count() == 1) {
            throw new IllegalArgumentException("Aynı isimle bir ürün zaten var.");
        }

        Product newProduct = new Product();
        newProduct.setName(newProductDto.getName());
        newProduct.setPrice(newProductDto.getPrice());
        newProduct.setStockQuantity(newProductDto.getStockQuantity());

        products.add(newProduct);
    }


    public void updateProduct(Long productId, ProductSaveRequest updatedProductDto) {
        Product existingProduct = products.stream()
                .filter(p -> p.getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Ürün bulunamadı."));

        if (products.stream().filter(p -> p.getName().equals(updatedProductDto.getName()) && !p.getId().equals(productId)).count() == 1) {
            throw new IllegalArgumentException("Aynı isimle bir ürün zaten var.");
        }

        existingProduct.setName(updatedProductDto.getName());
        existingProduct.setPrice(updatedProductDto.getPrice());
        existingProduct.setStockQuantity(updatedProductDto.getStockQuantity());
    }

    public void deleteProduct(Long productId) {
        Product productToDelete = products.stream()
                .filter(p -> p.getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Ürün bulunamadı."));

        products.remove(productToDelete);
    }
}
