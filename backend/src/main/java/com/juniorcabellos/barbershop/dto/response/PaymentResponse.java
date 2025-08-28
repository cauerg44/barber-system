package com.juniorcabellos.barbershop.dto.response;

import com.juniorcabellos.barbershop.entity.PaymentEntity;

public record PaymentResponse(
        Long id,
        String name
) {
    public PaymentResponse(PaymentEntity entity) {
        this(entity.getId(), entity.getName());
    }
}