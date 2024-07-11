package com.example.hellospring;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.example.hellospring.payment.PaymentService;

public class SingletonTest {

    @Test
    @DisplayName("4개의 PaymentService 객체는 모두 같은 참조를 갖는다")
    void singleton() {
        BeanFactory beanFactory = new AnnotationConfigApplicationContext(PaymentConfig.class);
        PaymentService paymentService1 = beanFactory.getBean(PaymentService.class);
        PaymentService paymentService2 = beanFactory.getBean(PaymentService.class);

        // 두 빈의 참조를 보면 똑같음, 즉 싱글톤
        assertThat(paymentService1).isEqualTo(paymentService2);
        assertThat(paymentService1 == paymentService2).isTrue();
        System.out.println("paymentService1 = " + paymentService1);
        System.out.println("paymentService2 = " + paymentService2);

        // 직접 구성정보 파일을 가져와 빈을 생성해보면, new 키워드가 2번 호출되어 참조가 다른 객체가 생성되는게 Java 기본
        // @Configuration 붙으면 메소드 호출을 여러번 해도 특별한 지시가 없으면 싱글톤으로 한 개만 생성
        PaymentConfig paymentConfig = beanFactory.getBean(PaymentConfig.class);
        PaymentService paymentService3 = paymentConfig.paymentService();
        PaymentService paymentService4 = paymentConfig.paymentService();

        assertThat(paymentService3).isEqualTo(paymentService4);
        assertThat(paymentService3 == paymentService4).isTrue();
        System.out.println("paymentService3 = " + paymentService3);
        System.out.println("paymentService4 = " + paymentService4);
    }

}
