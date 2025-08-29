package com.juniorcabellos.barbershop.repository;

import com.juniorcabellos.barbershop.entity.ServiceItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceItemRepository extends JpaRepository<ServiceItemEntity, Long> {
}
