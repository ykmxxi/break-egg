package com.example.order.order;

import com.example.order.product.Product;

public interface OrderPort {

    Product getProductById(final Long productId);

    void save(final Order order);

}
