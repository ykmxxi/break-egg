package com.example.hellospring;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

// import org.springframework.stereotype.Component;

// @Component // configuration 파일에 설정 없이 컴포넌트 애노테이션을 사용해도 됨
public class PaymentService {

    private final ExRateProvider exRateProvider;

    public PaymentService(final ExRateProvider exRateProvider) {
        this.exRateProvider = exRateProvider;
    }

    public Payment prepare(final Long orderId, final String currency, final BigDecimal foreignCurrencyAmount)
            throws IOException {
        BigDecimal exRate = exRateProvider.getExchangeRate(currency);
        BigDecimal convertedAmount = foreignCurrencyAmount.multiply(exRate);
        LocalDateTime validUntil = LocalDateTime.now().plusMinutes(30);

        return new Payment(orderId, currency, foreignCurrencyAmount,
                exRate, convertedAmount, validUntil);
    }

}
