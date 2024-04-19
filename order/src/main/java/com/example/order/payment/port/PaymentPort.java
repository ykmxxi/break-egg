package com.example.order.payment.port;

import com.example.order.order.domain.Order;
import com.example.order.payment.domain.Payment;

public interface PaymentPort {

    Order getOrder(final Long orderId);

    void pay(final int price, final String cardNumber);

    void save(Payment payment);

}
