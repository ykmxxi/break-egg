package com.example.hellospring;

import java.math.BigDecimal;

public class SimpleExRateProvider implements ExRateProvider {

    @Override
    public BigDecimal getExchangeRate(final String currency) {
        if (currency.equals("USD")) {
            return BigDecimal.valueOf(1000);
        }
        throw new IllegalArgumentException("지원되지 않는 통화입니다.");
    }

}
