package com.juniorcabellos.barbershop.repository;

import com.juniorcabellos.barbershop.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {

    @Query(value = """
        SELECT * FROM juniorsalao.tb_appointment tap
        WHERE tap.status = 'AGUARDANDO';
        """, nativeQuery = true)
    List<AppointmentEntity> findClientsWaiting();

    @Query(value = """
        SELECT * FROM juniorsalao.tb_appointment tap
        WHERE tap.status = 'CORTANDO';
        """, nativeQuery = true)
    List<AppointmentEntity> findClientsCutting();
}