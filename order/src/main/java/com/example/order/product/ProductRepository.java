package com.example.order.product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductRepository {

    private Map<Long, Product> persistence = new HashMap<>();
    private Long sequence = 0L;


    public void save(final Product product) {
        product.assignId(++sequence);
        persistence.put(product.getId(), product);
    }

    public List<Product> findAll() {
        return persistence.values()
                .stream()
                .toList();
    }

}
