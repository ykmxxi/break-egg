package com.example.order.product;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.order.product.domain.DiscountPolicy;
import com.example.order.product.domain.Product;
import com.example.order.product.service.AddProductRequest;
import com.example.order.product.service.GetProductResponse;
import com.example.order.product.service.ProductService;
import com.example.order.product.service.UpdateProductRequest;

@SpringBootTest
class ProductServiceTest {

    private static final Logger log = LoggerFactory.getLogger(ProductServiceTest.class);

    @Autowired
    private ProductService productService;

    @Test
    void 상품등록() {
        AddProductRequest request = createRequest();

        productService.addProduct(request);

        List<Product> products = productService.findAll();
        for (Product product : products) {
            log.info("Product id={}, name={}, price={}", product.getId(), product.getName(), product.getPrice());
        }

    }

    @Test
    void 상품조회() {
        // given
        productService.addProduct(createRequest());
        Long productId = 1L;

        // when
        GetProductResponse response = productService.getProduct(productId);

        // then
        assertThat(response).isNotNull();
    }

    @Test
    void 상품수정() {
        // given
        Long productId = 1L;
        UpdateProductRequest request = new UpdateProductRequest("상품수정", 2000, DiscountPolicy.NONE);
        productService.addProduct(createRequest());

        // when
        productService.updateProduct(productId, request);

        // then
        GetProductResponse response = productService.getProduct(productId);
        assertThat(response.name()).isEqualTo("상품수정");
        assertThat(response.price()).isEqualTo(2000);

    }

    private static AddProductRequest createRequest() {
        String name = "상품명";
        int price = 1000;
        DiscountPolicy discountPolicy = DiscountPolicy.NONE;
        return new AddProductRequest(name, price, discountPolicy);
    }

}
