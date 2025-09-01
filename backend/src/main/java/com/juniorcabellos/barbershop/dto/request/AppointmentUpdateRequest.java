package com.juniorcabellos.barbershop.dto.request;

import java.math.BigDecimal;
import java.util.Set;

public record AppointmentUpdateRequest(
        Long barberId,
        Long paymentId,
        Set<Long> serviceIds,
        BigDecimal totalValue
) {}