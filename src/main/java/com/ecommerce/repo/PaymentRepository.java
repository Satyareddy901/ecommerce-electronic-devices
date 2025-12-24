package com.ecommerce.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
	
	
	List<Payment> findByUserId(Long userId);
}

