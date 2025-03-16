package com.ubeyde.sample.controller;

import com.ubeyde.sample.annotations.ExcludeFromMachineCheck;
import com.ubeyde.sample.dto.DepositRequest;
import com.ubeyde.sample.entity.Deposit;
import com.ubeyde.sample.entity.Machine;
import com.ubeyde.sample.entity.Product;
import com.ubeyde.sample.entity.Purchase;
import com.ubeyde.sample.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/machine")
@Tag(name = "Machine Operations", description = "Operations about purchase, machine info etc.")
class MachineController {

    private final PurchaseService transactionService;
    private final MachineService machineService;
    private final ProductService productService;
    private final DepositService depositService;
    private final RefundService refundService;

    public MachineController(PurchaseService transactionService, MachineService machineService, ProductService productService, DepositService depositService, RefundService refundService) {
        this.transactionService = transactionService;
        this.machineService = machineService;
        this.productService = productService;
        this.depositService = depositService;
        this.refundService = refundService;
    }

    @GetMapping("/products")
    @Operation(summary = "Product List", description = "Lists the products available on the machine")
    public ResponseEntity<List<Product>> getProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @PostMapping("/purchase/{productId}")
    @Operation(summary = "Purchase Product", description = "Purchase product with product ID")
    public ResponseEntity<?> purchaseProduct(@PathVariable Long productId) {
        Purchase purchase = transactionService.processTransaction(productId);
        return ResponseEntity.ok(purchase);
    }

    @PostMapping("/deposit")
    @Operation(summary = "Deposit Money", description = "This endpoint is used for sending money to the machine")
    public ResponseEntity<Deposit> depositMoney(@RequestBody DepositRequest depositRequest) {
        Deposit deposit = depositService.depositMoney(depositRequest);
        return ResponseEntity.ok(deposit);
    }

    @ExcludeFromMachineCheck
    @GetMapping("/status")
    @Operation(summary = "Machine Status", description = "Shows the status and balance info")
    public ResponseEntity<Map<String, Object>> getStatus() {
        Machine machine = machineService.getMachineInfo();

        Map<String, Object> status = new HashMap<>();
        status.put("status", machine.getStatus());
        status.put("lastUpdated", machine.getLastUpdated());
        status.put("balance", machine.getBalanceTRY());

        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @GetMapping("/refund")
    @Operation(summary = "Refund Request", description = "This endpoint is used for requesting money to be sent to customer")
    public ResponseEntity<String> requestRefund() {

        boolean refundSuccess = refundService.handleRefund();

        if (refundSuccess) {
            return ResponseEntity.ok("Geri ödeme başarılı");
        } else {
            return ResponseEntity.status(500).body("Geri ödeme başarısız oldu. Tekrar deneyiniz.");
        }
    }

}
