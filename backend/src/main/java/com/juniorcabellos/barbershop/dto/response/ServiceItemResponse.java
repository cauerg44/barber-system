package com.juniorcabellos.barbershop.dto.response;

import com.juniorcabellos.barbershop.entity.ServiceItemEntity;

public record ServiceItemResponse(
        Long id,
        String name
) {
    public ServiceItemResponse(ServiceItemEntity entity) {
        this(entity.getId(), entity.getName());
    }
}