package com.example.hellospring;

import java.time.Clock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.example.hellospring.exrate.CachedExRateProvider;
import com.example.hellospring.exrate.WebApiExRateProvider;
import com.example.hellospring.payment.ExRateProvider;
import com.example.hellospring.payment.PaymentService;

@Configuration // 구성정보를 갖고 있음을 알려준다
@ComponentScan
public class PaymentConfig {

    @Bean
    public PaymentService paymentService() {
        return new PaymentService(cachedExRateProvider(), clock());
    }

    @Bean
    public ExRateProvider cachedExRateProvider() {
        return new CachedExRateProvider(exRateProvider());
    }

    @Bean
    public ExRateProvider exRateProvider() {
        return new WebApiExRateProvider();
    }

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }

}
