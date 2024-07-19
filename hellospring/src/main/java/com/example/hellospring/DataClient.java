package com.example.hellospring;

import java.math.BigDecimal;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.example.hellospring.order.Order;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

public class DataClient {

    public static void main(String[] args) {
        BeanFactory beanFactory = new AnnotationConfigApplicationContext(DataConfig.class);
        EntityManagerFactory emf = beanFactory.getBean(EntityManagerFactory.class);

        EntityManager em = emf.createEntityManager();
        Order order = new Order("100", BigDecimal.TEN);

        em.getTransaction().begin();
        em.persist(order);

        System.out.println(order.getId());

        em.getTransaction().commit();
        em.close();
    }

}
