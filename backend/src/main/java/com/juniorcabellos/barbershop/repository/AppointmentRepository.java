package com.juniorcabellos.barbershop.repository;

import com.juniorcabellos.barbershop.entity.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {

    @Query(value = """
        SELECT * FROM juniorsalao.tb_appointment appointment
        WHERE appointment.status = 'AGUARDANDO';
        """, nativeQuery = true)
    List<AppointmentEntity> findClientsWaiting();

    @Query(value = """
        SELECT * FROM juniorsalao.tb_appointment appointment
        WHERE appointment.status = 'CORTANDO';
        """, nativeQuery = true)
    List<AppointmentEntity> findClientsCutting();

    @Query(value = """
        SELECT SUM(appointment.total_value)
        FROM juniorsalao.tb_appointment appointment
        WHERE appointment.status = 'FINALIZADO' AND appointment.barber_id = :barberId
        """, nativeQuery = true)
    BigDecimal getTotalProfitForBarber(@Param("barberId") Long barberId);

    @Query(value = """
        SELECT SUM(appointment.total_value)
        FROM juniorsalao.tb_appointment appointment
        WHERE appointment.status = 'FINALIZADO' AND appointment.payment_id = :paymentId
        """, nativeQuery = true)
    BigDecimal getTotalProfitForPayment(@Param("paymentId") Long paymentId);

    @Query(value = """
        SELECT SUM(appointment.total_value)
        FROM juniorsalao.tb_appointment appointment
        WHERE appointment.status = 'FINALIZADO'
        """, nativeQuery = true)
    BigDecimal getTotalProfit();

    @Modifying
    @Transactional
    @Query(value = """
        DELETE FROM juniorsalao.tb_appointment
        WHERE status = 'FINALIZADO'
        """, nativeQuery = true)
    void deleteAllFinished();
}