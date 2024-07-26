package com.example.hellospring.order;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(final OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order createOrder(final String no, final BigDecimal total) {
        Order order = new Order(no, total);
        orderRepository.save(order);
        return order;
    }

    @Override
    public List<Order> createOrders(final List<OrderRequest> requests) {
        return requests.stream()
                .map(req -> createOrder(req.no(), req.total()))
                .toList();
    }

}
