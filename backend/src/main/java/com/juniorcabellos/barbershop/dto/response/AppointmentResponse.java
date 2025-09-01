package com.juniorcabellos.barbershop.dto.response;

import com.juniorcabellos.barbershop.entity.AppointmentEntity;
import com.juniorcabellos.barbershop.entity.enums.AppointmentStatus;

import java.math.BigDecimal;
import java.util.List;

public record AppointmentResponse(
        Long id,
        String clientName,
        BarberResponse barber,
        PaymentResponse payment,
        AppointmentStatus status,
        List<ServiceItemResponse> services,
        BigDecimal totalValue
) {
    public AppointmentResponse(AppointmentEntity entity) {
        this(
                entity.getId(),
                entity.getClientName(),
                new BarberResponse(entity.getBarber()),
                new PaymentResponse(entity.getPayment()),
                entity.getStatus(),
                entity.getServices().stream()
                        .map(ServiceItemResponse::new)
                        .toList(),
                entity.getTotalValue()
        );
    }
}
