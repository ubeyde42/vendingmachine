package com.ubeyde.sample.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class CurrencyService {

    private final Map<String, BigDecimal> exchangeRates;

    public CurrencyService() {
        exchangeRates = new HashMap<>();
        exchangeRates.put("USD", BigDecimal.valueOf(35.50));
        exchangeRates.put("EUR", BigDecimal.valueOf(36.80));
    }

    // Döviz kuru ile dönüşüm
    public Integer convertToTRY(Integer amount, String currency) {
        if (currency.equals("TRY")) {
            return amount;
        }

        BigDecimal rate = exchangeRates.get(currency.toUpperCase());
        if (rate == null) {
            throw new IllegalArgumentException("Geçersiz para birimi");
        }

        return  Math.round(amount*rate.floatValue());
    }
}

