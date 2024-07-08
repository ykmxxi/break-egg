package com.example.hellospring;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

class PaymentServiceTest {

    @Test
    void prepareTest() throws Exception {
        BeanFactory beanFactory = new AnnotationConfigApplicationContext(ObjectFactory.class);
        PaymentService paymentService = beanFactory.getBean(PaymentService.class);

        Payment result = paymentService.prepare(1L, "USD", BigDecimal.valueOf(50.7));
        assertThat(result).isInstanceOf(Payment.class);
        System.out.println("result = " + result);
    }

}
