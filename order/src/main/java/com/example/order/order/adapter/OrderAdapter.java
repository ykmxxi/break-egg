package com.example.order.order.adapter;

import org.springframework.stereotype.Component;

import com.example.order.order.port.OrderPort;
import com.example.order.order.domain.Order;
import com.example.order.product.domain.Product;
import com.example.order.product.adapter.ProductRepository;

@Component
public class OrderAdapter implements OrderPort {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    private OrderAdapter(final ProductRepository productRepository, final OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public Product getProductById(final Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));
    }

    @Override
    public void save(final Order order) {
        orderRepository.save(order);
    }

}
