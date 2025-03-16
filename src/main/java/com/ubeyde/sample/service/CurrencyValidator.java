package com.ubeyde.sample.service;

import com.ubeyde.sample.exception.InvalidCurrencyException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CurrencyValidator {

    private static final List<String> VALID_CURRENCY_DENOMINATIONS = List.of("USD", "EUR", "TRY");

    public void validateCurrency(String currencyCode, Integer amount) {
        if (!VALID_CURRENCY_DENOMINATIONS.contains(currencyCode)) {
            throw new InvalidCurrencyException("Geçersiz para birimi: " + currencyCode);
        }

        if (amount <= 0) {
            throw new InvalidCurrencyException("Ödeme tutarı geçersiz: " + amount);
        }

    }
}
