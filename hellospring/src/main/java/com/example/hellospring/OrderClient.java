package com.example.hellospring;

import java.math.BigDecimal;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.orm.jpa.JpaTransactionManager;

import com.example.hellospring.order.Order;
import com.example.hellospring.order.OrderService;
import com.example.hellospring.order.OrderServiceImpl;

public class OrderClient {

    public static void main(String[] args) {
        BeanFactory beanFactory = new AnnotationConfigApplicationContext(OrderConfig.class);
        OrderService orderServiceImpl = beanFactory.getBean(OrderServiceImpl.class);
        JpaTransactionManager transactionManager = beanFactory.getBean(JpaTransactionManager.class);

        Order order = orderServiceImpl.createOrder("0100", BigDecimal.TEN);
        System.out.println("order = " + order);
    }

}
