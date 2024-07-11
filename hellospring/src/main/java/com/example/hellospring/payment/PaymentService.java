package com.example.hellospring.payment;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;

// import org.springframework.stereotype.Component;

// @Component // configuration 파일에 설정 없이 컴포넌트 애노테이션을 사용해도 됨
public class PaymentService {

    private final ExRateProvider exRateProvider;
    private final Clock clock;

    public PaymentService(final ExRateProvider exRateProvider, final Clock clock) {
        this.exRateProvider = exRateProvider;
        this.clock = clock;
    }

    public Payment prepare(final Long orderId, final String currency, final BigDecimal foreignCurrencyAmount)
            throws IOException {
        BigDecimal exRate = exRateProvider.getExchangeRate(currency);

        return Payment.createPrepared(orderId, currency, foreignCurrencyAmount, exRate, LocalDateTime.now(clock));
    }

}
