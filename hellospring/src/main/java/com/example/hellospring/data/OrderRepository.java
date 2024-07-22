package com.example.hellospring.data;

import com.example.hellospring.order.Order;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class OrderRepository {

    @PersistenceContext
    private EntityManager em;

    public void save(final Order order) {
        em.persist(order);
    }

}
