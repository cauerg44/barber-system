package com.juniorcabellos.barbershop.dto.response;

import com.juniorcabellos.barbershop.entity.BarberEntity;

public record BarberResponse(
        Long id,
        String name
) {
    public BarberResponse(BarberEntity entity) {
        this(entity.getId(), entity.getName());
    }
}