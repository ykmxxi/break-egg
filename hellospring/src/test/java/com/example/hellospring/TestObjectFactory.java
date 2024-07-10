package com.example.hellospring;

import java.math.BigDecimal;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.hellospring.payment.ExRateProvider;
import com.example.hellospring.payment.ExRateProviderStub;
import com.example.hellospring.payment.PaymentService;

@Configuration
public class TestObjectFactory {

    @Bean
    public PaymentService paymentService() {
        return new PaymentService(exRateProvider());
    }

    @Bean
    public ExRateProvider exRateProvider() {
        return new ExRateProviderStub(BigDecimal.valueOf(1_000L));
    }

}
