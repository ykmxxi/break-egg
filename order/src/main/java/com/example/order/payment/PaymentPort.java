package com.example.order.payment;

import com.example.order.order.Order;

public interface PaymentPort {

    Order getOrder(final Long orderId);

    void pay(final int price, final String cardNumber);

    void save(Payment payment);

}
