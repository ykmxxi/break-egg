package com.example.order.product.port;

import java.util.List;

import com.example.order.product.domain.Product;

public interface ProductPort {

    void save(final Product product);

    List<Product> findAll();

    Product getProduct(final Long productId);

}
