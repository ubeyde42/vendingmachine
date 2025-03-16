package com.ubeyde.sample.service;

import com.ubeyde.sample.exception.InvalidCurrencyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencyValidator {

    private static final Logger logger = LoggerFactory.getLogger(CurrencyValidator.class);
    private static final List<String> VALID_CURRENCY_DENOMINATIONS = List.of("USD", "EUR", "TRY");

    public void validateCurrency(String currencyCode, Integer amount) {
        if (!VALID_CURRENCY_DENOMINATIONS.contains(currencyCode)) {
            logger.warn("Invalid currency deposited : " + currencyCode +  "amount : " + amount);
            throw new InvalidCurrencyException("Geçersiz para birimi: " + currencyCode);
        }

        if (amount <= 0) {
            logger.warn("Invalid amount deposited : " + currencyCode +  "amount : " + amount);
            throw new InvalidCurrencyException("Ödeme tutarı geçersiz: " + amount);
        }

    }
}
