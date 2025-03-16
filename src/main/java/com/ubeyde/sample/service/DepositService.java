package com.ubeyde.sample.service;

import com.ubeyde.sample.dto.DepositRequest;
import com.ubeyde.sample.entity.Deposit;
import com.ubeyde.sample.event.MoneyDepositedEvent;
import com.ubeyde.sample.event.VendingMachineEventPublisher;
import com.ubeyde.sample.repository.DepositRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DepositService {

    private final DepositRepository depositRepository;
    private final CurrencyService currencyService;
    private final CurrencyValidator currencyValidator;
    private final VendingMachineEventPublisher eventPublisher;

    @Autowired
    public DepositService(DepositRepository depositRepository, CurrencyService currencyService, CurrencyValidator currencyValidator,  VendingMachineEventPublisher eventPublisher) {
        this.depositRepository = depositRepository;
        this.currencyService = currencyService;
        this.currencyValidator = currencyValidator;
        this.eventPublisher = eventPublisher;
    }

    public Deposit depositMoney(DepositRequest depositRequest) {

        currencyValidator.validateCurrency(depositRequest.getCurrency(), depositRequest.getAmount());
        Deposit deposit = new Deposit(depositRequest.getAmount(), depositRequest.getCurrency());

        depositRepository.save(deposit);

        MoneyDepositedEvent moneyDepositedEvent = new MoneyDepositedEvent(currencyService.convertToTRY(depositRequest.getAmount(), depositRequest.getCurrency()));
        eventPublisher.publishEvent(moneyDepositedEvent);

        return deposit;
    }

}

