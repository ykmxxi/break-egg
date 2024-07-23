package com.example.hellospring.order;

import java.math.BigDecimal;

import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final JpaTransactionManager jpaTransactionManager;

    public OrderService(final OrderRepository orderRepository, final JpaTransactionManager jpaTransactionManager) {
        this.orderRepository = orderRepository;
        this.jpaTransactionManager = jpaTransactionManager;
    }

    public Order createOrder(final String no, final BigDecimal total) {
        Order order = new Order(no, total);

        return new TransactionTemplate(jpaTransactionManager).execute(status -> {
            orderRepository.save(order);
            return order;
        });
    }

}
