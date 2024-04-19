package com.example.order.product;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ProductTest {

    @Test
    void update() {
        // given
        Product product = new Product("상품명", 1000, DiscountPolicy.NONE);

        // when
        product.update("상품수정", 2000, DiscountPolicy.NONE);

        // then
        assertThat(product.getName()).isEqualTo("상품수정");
        assertThat(product.getPrice()).isEqualTo(2000);
    }

}
