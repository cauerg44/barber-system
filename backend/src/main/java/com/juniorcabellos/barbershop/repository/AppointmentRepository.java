package com.juniorcabellos.barbershop.repository;

import com.juniorcabellos.barbershop.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {
}
