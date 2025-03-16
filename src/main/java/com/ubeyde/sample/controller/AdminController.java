package com.ubeyde.sample.controller;

import com.ubeyde.sample.dto.ProductSaveRequest;
import com.ubeyde.sample.entity.Machine;
import com.ubeyde.sample.enums.MachineStatus;
import com.ubeyde.sample.service.MachineService;
import com.ubeyde.sample.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@Tag(name = "Admin Operations", description = "Operations for managing machine and products")
public class AdminController {

    private final MachineService machineService;
    private final ProductService productService;

    public AdminController(MachineService machineService, ProductService productService) {
        this.machineService = machineService;
        this.productService = productService;
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
        productService.addNewProduct(product);
        return ResponseEntity.ok("Product added successfully.");
    }

}
