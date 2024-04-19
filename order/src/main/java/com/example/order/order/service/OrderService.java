package com.example.order.order.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.order.order.domain.Order;
import com.example.order.order.port.OrderPort;
import com.example.order.product.domain.Product;

@RestController
@RequestMapping("/orders")
public class OrderService {

    private final OrderPort orderPort;

    OrderService(final OrderPort orderPort) {
        this.orderPort = orderPort;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Void> createOrder(@RequestBody final CreateOrderRequest request) {
        Product product = orderPort.getProductById(request.productId());

        Order order = new Order(product, request.quantity());

        orderPort.save(order);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
