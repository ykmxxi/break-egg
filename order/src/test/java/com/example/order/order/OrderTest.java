package com.example.order.order;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.example.order.product.DiscountPolicy;
import com.example.order.product.Product;

class OrderTest {

    @Test
    void getTotalPrice() {
        // given
        Order order = new Order(new Product("상품명1", 1000, DiscountPolicy.NONE), 2);

        // when
        int result = order.getTotalPrice();

        // then
        assertThat(result).isEqualTo(2000);
    }

}
