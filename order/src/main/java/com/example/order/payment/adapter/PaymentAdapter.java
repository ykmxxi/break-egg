package com.example.order.payment.adapter;

import org.springframework.stereotype.Component;

import com.example.order.order.domain.Order;
import com.example.order.order.adapter.OrderRepository;
import com.example.order.payment.domain.Payment;
import com.example.order.payment.port.PaymentPort;

@Component
public class PaymentAdapter implements PaymentPort {

    private final PaymentGateway paymentGateway;
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public PaymentAdapter(
            final PaymentGateway paymentGateway,
            final PaymentRepository paymentRepository,
            final OrderRepository orderRepository
    ) {
        this.paymentGateway = paymentGateway;
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public Order getOrder(final Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("주문이 존재하지 않습니다."));
    }

    @Override
    public void pay(final int price, final String cardNumber) {
        paymentGateway.execute(price, cardNumber);
    }

    @Override
    public void save(final Payment payment) {
        paymentRepository.save(payment);
    }

}
