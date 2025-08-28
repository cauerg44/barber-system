package com.juniorcabellos.barbershop.repository;

import com.juniorcabellos.barbershop.entity.BarberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BarberRepository extends JpaRepository<BarberEntity, Long> {
}
