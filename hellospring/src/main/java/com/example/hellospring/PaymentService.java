package com.example.hellospring;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public abstract class PaymentService {

    public Payment prepare(final Long orderId, final String currency, final BigDecimal foreignCurrencyAmount)
            throws IOException {
        BigDecimal exRate = getExchangeRate(currency);
        BigDecimal convertedAmount = foreignCurrencyAmount.multiply(exRate);
        LocalDateTime validUntil = LocalDateTime.now().plusMinutes(30);

        return new Payment(orderId, currency, foreignCurrencyAmount,
                exRate, convertedAmount, validUntil);
    }

    abstract BigDecimal getExchangeRate(final String currency) throws IOException;

}
