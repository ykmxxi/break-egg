package com.example.hellospring;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Payment {

    private Long orderId;
    private String currency; // 현재 사용하는 통화
    private BigDecimal foreignCurrencyAmount; // 외국 통화 기준 결제 금액
    private BigDecimal exchangeRate; // 환율
    private BigDecimal convertedAmount; // 환산 금액
    private LocalDateTime validUntil; // 환산 금액 유효 기간

    public Payment(
            final Long orderId,
            final String currency,
            final BigDecimal foreignCurrencyAmount,
            final BigDecimal exchangeRate,
            final BigDecimal convertedAmount,
            final LocalDateTime validUntil
    ) {
        this.orderId = orderId;
        this.currency = currency;
        this.foreignCurrencyAmount = foreignCurrencyAmount;
        this.exchangeRate = exchangeRate;
        this.convertedAmount = convertedAmount;
        this.validUntil = validUntil;
    }

    public Long getOrderId() {
        return orderId;
    }

    public String getCurrency() {
        return currency;
    }

    public BigDecimal getForeignCurrencyAmount() {
        return foreignCurrencyAmount;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public BigDecimal getConvertedAmount() {
        return convertedAmount;
    }

    public LocalDateTime getValidUntil() {
        return validUntil;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "orderId=" + orderId +
                ", currency='" + currency + '\'' +
                ", foreignCurrencyAmount=" + foreignCurrencyAmount +
                ", exchangeRate=" + exchangeRate +
                ", convertedAmount=" + convertedAmount +
                ", validUntil=" + validUntil +
                '}';
    }

}
