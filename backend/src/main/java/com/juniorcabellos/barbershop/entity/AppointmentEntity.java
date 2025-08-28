package com.juniorcabellos.barbershop.entity;

import com.juniorcabellos.barbershop.entity.enums.AppointmentStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(schema = "juniorsalao", name = "tb_appointment")
public class AppointmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "client_name", nullable = false)
    private String clientName;

    @ManyToOne
    @JoinColumn(name = "barber_id", nullable = false)
    private BarberEntity barber;

    @ManyToOne
    @JoinColumn(name = "payment_id", nullable = false)
    private PaymentEntity payment;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AppointmentStatus status = AppointmentStatus.AGUARDANDO;

    @Column(name = "total_value", nullable = false)
    private BigDecimal totalValue;

    @ManyToMany
    @JoinTable(
            name = "tb_appointment_services",
            schema = "juniorsalao",
            joinColumns = @JoinColumn(name = "appointment_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private Set<ServiceItemEntity> services = new HashSet<>();

    public AppointmentEntity() {
    }

    public AppointmentEntity(Long id, String clientName, BarberEntity barber, PaymentEntity payment, AppointmentStatus status, BigDecimal totalValue) {
        this.id = id;
        this.clientName = clientName;
        this.barber = barber;
        this.payment = payment;
        this.status = status;
        this.totalValue = totalValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public BarberEntity getBarber() {
        return barber;
    }

    public void setBarber(BarberEntity barber) {
        this.barber = barber;
    }

    public PaymentEntity getPayment() {
        return payment;
    }

    public void setPayment(PaymentEntity payment) {
        this.payment = payment;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

    public Set<ServiceItemEntity> getServices() {
        return services;
    }

    public void addService(ServiceItemEntity serviceItem) {
        services.add(serviceItem);
    }

    public void removeService(ServiceItemEntity serviceItem) {
        services.remove(serviceItem);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AppointmentEntity that = (AppointmentEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}