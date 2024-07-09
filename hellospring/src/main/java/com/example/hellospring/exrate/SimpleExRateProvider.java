package com.example.hellospring.exrate;

import java.math.BigDecimal;

import com.example.hellospring.payment.ExRateProvider;

public class SimpleExRateProvider implements ExRateProvider {

    @Override
    public BigDecimal getExchangeRate(final String currency) {
        if (currency.equals("USD")) {
            return BigDecimal.valueOf(1000);
        }
        throw new IllegalArgumentException("지원되지 않는 통화입니다.");
    }

}
