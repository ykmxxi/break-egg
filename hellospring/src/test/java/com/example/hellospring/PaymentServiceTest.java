package com.example.hellospring;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class PaymentServiceTest {

    @Test
    void prepareTest() throws Exception {
        PaymentService paymentService = new WebApiExRatePaymentService();

        Payment result = paymentService.prepare(1L, "USD", BigDecimal.valueOf(50.7));
        assertThat(result).isInstanceOf(Payment.class);
        System.out.println("result = " + result);
    }

}
