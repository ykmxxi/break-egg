package com.example.hellospring.data;

import com.example.hellospring.order.Order;
import com.example.hellospring.order.OrderRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class JpaOrderRepository implements OrderRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void save(final Order order) {
        em.persist(order);
    }

}
