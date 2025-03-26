package com.ubeyde.sample.controller;

import com.ubeyde.sample.dto.ProductSaveRequest;
import com.ubeyde.sample.entity.Machine;
import com.ubeyde.sample.enums.MachineStatus;
import com.ubeyde.sample.service.MachineService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@Tag(name = "Admin Operations", description = "Operations for managing machine and products")
public class AdminController {

    private final MachineService machineService;

    public AdminController(MachineService machineService) {
        this.machineService = machineService;
    }

    @PostMapping("/status")
    @Operation(summary = "Update machine status", description = "This endpoint is used for changing the status of the machine",
            security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Machine> updateStatus(@RequestBody MachineStatus status) {
        Machine updatedBalance = machineService.updateMachineInfo(status);
        return new ResponseEntity<>(updatedBalance, HttpStatus.OK);
    }

    @PostMapping("/addProduct")
    @Operation(summary = "Add new product", description = "This endpoint is used for adding new product to the machine",
            security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<String> addProduct(@RequestBody ProductSaveRequest product) {
        machineService.addNewProduct(product);
        return ResponseEntity.ok("Ürün eklendi.");
    }

    @PostMapping("/updateProduct/{productId}")
    @Operation(summary = "Update product", description = "This endpoint is used for updating an existing product",
            security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<String> updateProduct(@PathVariable Long productId, @RequestBody ProductSaveRequest productSaveRequest) {
        machineService.updateProduct(productId, productSaveRequest);
        return ResponseEntity.ok("Ürün bilgileri güncellendi.");
    }

    @DeleteMapping("/deleteProduct/{productId}")
    @Operation(summary = "Delete product", description = "This endpoint is used for deleting an existing product",
            security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<String> deleteProduct(@PathVariable Long productId) {
        machineService.deleteProduct(productId);
        return ResponseEntity.ok("Ürün makineden kaldırıldı.");
    }

}
