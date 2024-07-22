package com.example.hellospring;

import java.math.BigDecimal;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.example.hellospring.data.OrderRepository;
import com.example.hellospring.order.Order;

public class DataClient {

    public static void main(String[] args) {
        BeanFactory beanFactory = new AnnotationConfigApplicationContext(DataConfig.class);
        OrderRepository repository = beanFactory.getBean(OrderRepository.class);
        JpaTransactionManager transactionManager = beanFactory.getBean(JpaTransactionManager.class);

        try {
            // tx begin
            new TransactionTemplate(transactionManager).execute(status -> {
                Order order = new Order("100", BigDecimal.TEN);
                repository.save(order);

                System.out.println("order = " + order);

                Order duplicatedOrder = new Order("100", BigDecimal.ONE);
                // org.springframework.dao.DataIntegrityViolationException
                // JDBC, JPA, MyBatis 등 어떤 기술을 사용하든 스프링이 예외를 추상화해 처리
                repository.save(duplicatedOrder);
                return null;
            });
        } catch (DataIntegrityViolationException e) {
            System.out.println("주문번호 중복 복구 작업");
        }

        // tx commit
    }

}
