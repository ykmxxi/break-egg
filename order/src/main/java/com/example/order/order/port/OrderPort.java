package com.example.order.order.port;

import com.example.order.order.domain.Order;
import com.example.order.product.domain.Product;

public interface OrderPort {

    Product getProductById(final Long productId);

    void save(final Order order);

}
