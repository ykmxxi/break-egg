package com.example.order.product;

import java.util.List;

public interface ProductPort {

    void save(final Product product);

    List<Product> findAll();

    Product getProduct(final Long productId);

}
