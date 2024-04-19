package com.example.order.payment.adapter;

import org.springframework.stereotype.Component;

@Component
public class ConsolePaymentGateway implements PaymentGateway {

    @Override
    public void execute(final int price, final String cardNumber) {
        System.out.println("결제 완료");
    }

}
