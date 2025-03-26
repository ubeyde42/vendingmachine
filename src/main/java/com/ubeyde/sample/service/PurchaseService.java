package com.ubeyde.sample.service;


import com.ubeyde.sample.entity.Product;
import com.ubeyde.sample.entity.Purchase;
import com.ubeyde.sample.event.ProductBoughtEvent;
import com.ubeyde.sample.event.VendingMachineEventPublisher;
import com.ubeyde.sample.exception.InsufficientBalanceException;
import com.ubeyde.sample.exception.ProductNotFoundException;
import com.ubeyde.sample.repository.PurchaseRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class PurchaseService {

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

    @Transactional
    public Purchase processTransaction(Long productId) {
        Product product = productService.getProductById(productId);

        Integer currentMachineBalance = machineService.getMachineBalance();

        if (product == null || product.getStockQuantity() <= 0) {
            throw new ProductNotFoundException("Ürün stokta yok.");
        }

        Integer price = product.getPrice();
        if (currentMachineBalance-price < 0) {
           throw new InsufficientBalanceException(Math.abs(currentMachineBalance-price));
        }

        try {
            /*
            purchase record will be saved and related event published for listeners
             */
            Purchase purchase = Purchase.builder()
                    .product(product)
                    .amount(price)
                    .build();
            Purchase savedPurchase = purchaseRepository.save(purchase);

            eventPublisher.publishEvent((new ProductBoughtEvent(
                    product.getId(),
                    price
            )));

            logger.info("A purchase operation completed. Product: " + product.getName() + " price : "+ price);

            return savedPurchase;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}
