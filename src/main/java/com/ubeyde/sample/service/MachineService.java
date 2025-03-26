package com.ubeyde.sample.service;

import com.ubeyde.sample.dto.ProductSaveRequest;
import com.ubeyde.sample.entity.Machine;
import com.ubeyde.sample.entity.Product;
import com.ubeyde.sample.enums.MachineStatus;
import com.ubeyde.sample.event.*;
import com.ubeyde.sample.repository.MachineRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MachineService {

    private final MachineRepository machineRepository;
    private final VendingMachineEventPublisher eventPublisher;

    public MachineService(MachineRepository machineRepository, VendingMachineEventPublisher eventPublisher) {
        this.machineRepository = machineRepository;
        this.eventPublisher = eventPublisher;
    }

    @EventListener
    public void handleMoneyDepositedEvent(MoneyDepositedEvent event) {
        increaseMachineBalance(event.getAmount());
    }

    @EventListener
    public void handleRefundRequestedEvent(RefundRequestedEvent event) {
        //until refund operation finished, machine status will not be active
        updateMachineStatus(MachineStatus.REFUND_PENDING);
    }

    @EventListener
    public void handleRefundSuccessEvent(RefundSuccessEvent event) {
        updateMachineStatus(MachineStatus.ACTIVE);
        resetMachineBalance();
    }

    @EventListener
    public void handlePurchaseNotifyPeriodReachedEvent(PurchaseNotifyPeriodReachedEvent event) {
        //if period reached, machine will be closed
        updateMachineStatus(MachineStatus.MAINTENANCE);
    }

    public Machine updateMachineInfo(MachineStatus status) {
        Machine machine = getMachineInfo();
        machine.setStatus(status);
        machine.setLastUpdated(new Date());

        return machineRepository.save(machine);
    }

    public List<Product> getAllProducts() {
        Machine machine = getMachineInfo();
        return machine.getProducts();
    }

    public void addNewProduct(ProductSaveRequest product) {
        Machine machine = getMachineInfo();
        machine.addNewProduct(product);
        machineRepository.save(machine);
    }

    public void updateProduct(Long productId, ProductSaveRequest productSaveRequest) {
        Machine machine = getMachineInfo();
        machine.updateProduct(productId,productSaveRequest);
        machineRepository.save(machine);
    }

    public void deleteProduct(Long productId) {
        Machine machine = getMachineInfo();
        machine.deleteProduct(productId);
        machineRepository.save(machine);
    }

    public Machine getMachineInfo() {
        return machineRepository.findById(1L).orElse(new Machine());
    }


    public void increaseMachineBalance(Integer amount) {
        Machine machineInfo = getMachineInfo();
        machineInfo.setBalanceTRY(machineInfo.getBalanceTRY()+amount);
        machineRepository.save(machineInfo);
    }

    public void decreaseMachineBalance(Integer amount) {
        Machine machineInfo = getMachineInfo();
        machineInfo.setBalanceTRY(machineInfo.getBalanceTRY() - amount);
        machineRepository.save(machineInfo);
    }

    public void resetMachineBalance() {
        Machine machineInfo = getMachineInfo();
        machineInfo.setBalanceTRY(0);
        machineRepository.save(machineInfo);
    }

    private void updateMachineStatus(MachineStatus machineStatus) {
        Machine machine = getMachineInfo();
        machine.setStatus(machineStatus);
        machine.setLastUpdated(new Date());

        machineRepository.save(machine);
    }

    public void purchaseProduct(Long productId) {
        Machine machine = getMachineInfo();
        Integer price = machine.purchaseProduct(productId);
        machineRepository.save(machine);

        eventPublisher.publishEvent((new ProductBoughtEvent(
                productId,
                price
        )));

    }


}

