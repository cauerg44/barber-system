package com.juniorcabellos.barbershop.dto.response;

import com.juniorcabellos.barbershop.entity.BarberEntity;
import com.juniorcabellos.barbershop.entity.enums.AppointmentStatus;

import java.math.BigDecimal;
import java.util.List;

public record AppointmentResponse(
        Long id,
        String clientName,
        BarberResponse barber,
        PaymentResponse paymentMethod,
        AppointmentStatus status,
        List<ServiceItemResponse> services,
        BigDecimal totalValue
) {
}