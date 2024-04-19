package com.example.order.payment.adapter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.order.payment.domain.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

}
