package com.juniorcabellos.barbershop.service;

import com.juniorcabellos.barbershop.dto.response.PaymentResponse;
import com.juniorcabellos.barbershop.entity.BarberEntity;
import com.juniorcabellos.barbershop.entity.PaymentEntity;
import com.juniorcabellos.barbershop.repository.PaymentRepository;
import com.juniorcabellos.barbershop.service.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PaymentService {

    private final PaymentRepository repository;

    public PaymentService(PaymentRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<PaymentResponse> findAll() {
        List<PaymentEntity> list = repository.findAll();
        return list.stream()
                .map(it -> new PaymentResponse(it.getId(), it.getName()))
                .toList();
    }

    @Transactional(readOnly = true)
    public PaymentResponse findById(Long id) {
        PaymentEntity paymentEntity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Não existe essa forma de pagamento"));
        return new PaymentResponse(paymentEntity);
    }
}