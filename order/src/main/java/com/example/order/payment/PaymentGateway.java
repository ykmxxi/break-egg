package com.example.order.payment;

public interface PaymentGateway {

    void execute(final int price, final String cardNumber);

}
