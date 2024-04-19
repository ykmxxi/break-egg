package com.example.order.product.adapter;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.order.product.domain.Product;
import com.example.order.product.port.ProductPort;

@Component
public class ProductAdapter implements ProductPort {

    private final ProductRepository productRepository;

    ProductAdapter(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void save(final Product product) {
        productRepository.save(product);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product getProduct(final Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("상품이 존재하지 않습니다."));
    }

}
