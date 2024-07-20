package com.example.hellospring.data;

import org.springframework.stereotype.Repository;

import com.example.hellospring.order.Order;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

@Repository
public class OrderRepository {

    private final EntityManagerFactory emf;

    public OrderRepository(final EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void save(final Order order) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            em.persist(order);
            em.flush();

            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

}
