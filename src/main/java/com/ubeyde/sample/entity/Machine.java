package com.ubeyde.sample.entity;

import com.ubeyde.sample.enums.MachineStatus;
import jakarta.persistence.*;
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

    public void purchaseProduct(Long productId) {

            if (status != MachineStatus.ACTIVE) {
                throw new IllegalStateException("Makine aktif değil, işlem yapılamaz.");
            }

            Product product = products.stream()
                    .filter(p -> p.getId().equals(productId))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Ürün bulunamadı."));

            if (product.getStockQuantity() <= 0) {
                throw new IllegalStateException("Stokta yeterli ürün yok.");
            }

            if (balanceTRY.compareTo(product.getPrice()) < 0) {
                throw new IllegalArgumentException("Yetersiz ödeme.");
            }

            // 🟢 İşlem başarılı: Stok ve kasa güncelleniyor
            product.decreaseStock();
            setBalanceTRY(balanceTRY - product.getPrice());

            /*balance = balance.add(product.getPrice());

            BigDecimal change = payment.subtract(product.getPrice());

            // 📢 Domain Event yayınla
            raiseEvent(new ProductPurchasedEvent(productId, product.getName(), payment, change));

            return change; // Para üstü
*/
    }
}
