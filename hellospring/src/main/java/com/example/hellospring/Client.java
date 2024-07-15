package com.example.hellospring;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.example.hellospring.payment.Payment;
import com.example.hellospring.payment.PaymentService;

public class Client {

    public static void main(String[] args) throws InterruptedException {
        BeanFactory beanFactory = new AnnotationConfigApplicationContext(PaymentConfig.class); // BeanFactory 구현체
        PaymentService paymentService = beanFactory.getBean(PaymentService.class);

        Payment payment1 = paymentService.prepare(100L, "USD", BigDecimal.valueOf(50.7));
        System.out.println("payment1 = " + payment1);
        System.out.println("---------------------------");

        // 1초만 지난 상황이라 API 호출 없이 Cache 정보 이용
        TimeUnit.SECONDS.sleep(1);

        Payment payment2 = paymentService.prepare(100L, "USD", BigDecimal.valueOf(50.7));
        System.out.println("payment2 = " + payment2);
        System.out.println("---------------------------");

        TimeUnit.SECONDS.sleep(3);
        // 3초가 지나 새로운 API 호출이 일어남
        Payment payment3 = paymentService.prepare(100L, "USD", BigDecimal.valueOf(50.7));
        System.out.println("payment3 = " + payment3);
    }

}
