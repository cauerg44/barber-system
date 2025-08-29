package com.juniorcabellos.barbershop.dto.request;

import com.juniorcabellos.barbershop.entity.enums.AppointmentStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.List;

public record AppointmentCreateRequest(
        @NotBlank(message = "Deve ter nome de cliente")
        String clientName,

        @NotNull(message = "Deve ter um barbeiro")
        Long barberId,

        @NotNull(message = "Deve ter uma forma de pagamento")
        Long paymentId,

        @NotNull(message = "Campo não pode ser inválido")
        AppointmentStatus status,

        List<Long> servicesIds,

        @NotNull(message = "Campo não pode ser inválido")
        @Positive(message = "Preço deve ser positivo")
        BigDecimal totalValue
) {
}