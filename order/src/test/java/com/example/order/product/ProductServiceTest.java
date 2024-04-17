package com.example.order.product;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ProductServiceTest {

    private static final Logger log = LoggerFactory.getLogger(ProductServiceTest.class);

    private ProductService productService;
    private ProductPort productPort;
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        this.productRepository = new ProductRepository();
        this.productPort = new ProductAdapter(productRepository);
        this.productService = new ProductService(productPort);
    }

    @Test
    void 상품등록() {
        AddProductRequest request = createRequest();

        productService.addProduct(request);

        List<Product> products = productService.findAll();
        for (Product product : products) {
            log.info("Product id={}, name={}, price={}", product.getId(), product.getName(), product.getPrice());
        }

    }

    private static AddProductRequest createRequest() {
        String name = "상품명";
        int price = 1000;
        DiscountPolicy discountPolicy = DiscountPolicy.NONE;
        return new AddProductRequest(name, price, discountPolicy);
    }

}
