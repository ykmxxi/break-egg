package com.example.order.payment.adapter;

public interface PaymentGateway {

    void execute(final int price, final String cardNumber);

}
