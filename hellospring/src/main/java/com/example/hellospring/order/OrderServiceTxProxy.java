package com.example.hellospring.order;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

public class OrderServiceTxProxy implements OrderService {

    private final OrderService target;
    private final PlatformTransactionManager transactionManager;

    public OrderServiceTxProxy(final OrderService target, final PlatformTransactionManager transactionManager) {
        this.target = target;
        this.transactionManager = transactionManager;
    }

    @Override
    public Order createOrder(final String no, final BigDecimal total) {
        return new TransactionTemplate(transactionManager).execute(status ->
                target.createOrder(no, total)
        );
    }

    @Override
    public List<Order> createOrders(final List<OrderRequest> requests) {
        return new TransactionTemplate(transactionManager).execute(status ->
                target.createOrders(requests)
        );
    }

}
