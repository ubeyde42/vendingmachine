package com.ubeyde.sample.service;


import com.ubeyde.sample.entity.Product;
import com.ubeyde.sample.entity.Purchase;
import com.ubeyde.sample.event.ProductBoughtEvent;
import com.ubeyde.sample.event.PurchaseNotifyPeriodReachedEvent;
import com.ubeyde.sample.event.VendingMachineEventPublisher;
import com.ubeyde.sample.repository.PurchaseRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class PurchaseService {


    private static final int PURCHASE_NOTIFY_PERIOD = 5;

    private static final Logger logger = LoggerFactory.getLogger(PurchaseService.class);

    private final ProductService productService;
    private final PurchaseRepository purchaseRepository;
    private final MachineService machineService;
    private final VendingMachineEventPublisher eventPublisher;

    public PurchaseService(ProductService productService, PurchaseRepository purchaseRepository, MachineService machineService, VendingMachineEventPublisher eventPublisher) {
        this.productService = productService;
        this.purchaseRepository = purchaseRepository;
        this.machineService = machineService;
        this.eventPublisher = eventPublisher;
    }

    @EventListener
    public void handleProductBoughtEvent(ProductBoughtEvent event) {
        savePurchase(event.getProductId(),event.getAmountPaid());
    }


    @Transactional
    public void savePurchase(Long productId, Integer amountPaid) {

        Product product = productService.getProductById(productId);

        Purchase purchase = Purchase.builder()
                .product(product)
                .amount(amountPaid)
                .build();
        purchaseRepository.save(purchase);

        //check the reached count and notify if period reached
        long totalPurchases = purchaseRepository.count();
        if (totalPurchases % PURCHASE_NOTIFY_PERIOD == 0 && totalPurchases != 0) {
            eventPublisher.publishEvent(new PurchaseNotifyPeriodReachedEvent(totalPurchases));
        }
    }
}
