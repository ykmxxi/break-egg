package com.example.order.payment.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.order.order.domain.Order;
import com.example.order.payment.port.PaymentPort;
import com.example.order.payment.domain.Payment;

@RestController
@RequestMapping("/payments")
public class PaymentService {

    private final PaymentPort paymentPort;

    PaymentService(final PaymentPort paymentPort) {
        this.paymentPort = paymentPort;
    }

    @Transactional
    @PostMapping
    public ResponseEntity<Void> payment(@RequestBody final PaymentRequest request) {
        Order order = paymentPort.getOrder(request.orderId());

        Payment payment = new Payment(order, request.cardNumber());

        paymentPort.pay(payment.getPrice(), payment.getCardNumber());
        paymentPort.save(payment);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
