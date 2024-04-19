package com.example.order.order;

import org.junit.jupiter.api.Test;

public class OrderServiceTest {

    private OrderService orderService;

    @Test
    void 상품주문() {
        // given
        Long productId = 1L;
        int quantity = 2;
        CreateOrderRequest request = new CreateOrderRequest(productId, quantity);

        // when
        orderService.createOrder(request);

        // then
    }

}
