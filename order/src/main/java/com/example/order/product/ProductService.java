package com.example.order.product;

import java.util.List;

public class ProductService {

    private final ProductPort productPort;

    ProductService(final ProductPort productPort) {
        this.productPort = productPort;
    }

    public void addProduct(final AddProductRequest request) {
        Product product = new Product(request.name(), request.price(), request.discountPolicy());

        productPort.save(product);
    }

    public List<Product> findAll() {
        return productPort.findAll();
    }

}
