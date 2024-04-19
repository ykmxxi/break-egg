package com.example.order.product;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.example.order.product.domain.DiscountPolicy;

class DiscountPolicyTest {

    @Test
    void applyDiscount() {
        assertThat(DiscountPolicy.NONE.applyDiscount(1000)).isEqualTo(1000);

        assertThat(DiscountPolicy.FIX_1000_AMOUNT.applyDiscount(2000)).isEqualTo(1000);
    }

}
