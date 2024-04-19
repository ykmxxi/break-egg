package com.example.order.product.adapter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.order.product.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> { // JPA 적용

}
