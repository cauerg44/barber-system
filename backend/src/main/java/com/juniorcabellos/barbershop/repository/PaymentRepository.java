package com.juniorcabellos.barbershop.repository;

import com.juniorcabellos.barbershop.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
}