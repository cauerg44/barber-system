package com.juniorcabellos.barbershop.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(schema = "juniorsalao", name = "tb_payment")
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String method;

    public PaymentEntity() {
    }

    public PaymentEntity(Long id, String name) {
        this.id = id;
        this.method = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return method;
    }

    public void setName(String name) {
        this.method = name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        PaymentEntity that = (PaymentEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}