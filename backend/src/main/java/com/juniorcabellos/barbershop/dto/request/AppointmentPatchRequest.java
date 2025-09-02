package com.juniorcabellos.barbershop.dto.request;

import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record AppointmentPatchRequest(
        String clientName,
        Long barberId,
        Long paymentId,
        String status,

        @Positive(message = "O valor total deve ser positivo")
        BigDecimal totalValue
) {}
