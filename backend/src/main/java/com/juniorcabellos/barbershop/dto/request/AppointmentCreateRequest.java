package com.juniorcabellos.barbershop.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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

        @NotNull(message = "Deve ter pelo menos um serviço")
        @NotEmpty(message = "A lista de serviços não pode estar vazia")
        List<Long> servicesIds,

        @NotNull(message = "Campo não pode ser inválido")
        @Positive(message = "Preço deve ser positivo")
        BigDecimal totalValue
) {
}