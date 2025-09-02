package com.juniorcabellos.barbershop.service;

import com.juniorcabellos.barbershop.entity.BarberEntity;
import com.juniorcabellos.barbershop.entity.PaymentEntity;
import com.juniorcabellos.barbershop.entity.ServiceItemEntity;
import com.juniorcabellos.barbershop.repository.BarberRepository;
import com.juniorcabellos.barbershop.repository.PaymentRepository;
import com.juniorcabellos.barbershop.repository.ServiceItemRepository;
import org.springframework.stereotype.Service;

@Service
public class EntityFinder {

    private final BarberRepository barberRepository;
    private final PaymentRepository paymentRepository;
    private final ServiceItemRepository serviceItemRepository;

    public EntityFinder(BarberRepository barberRepository, PaymentRepository paymentRepository, ServiceItemRepository serviceItemRepository) {
        this.barberRepository = barberRepository;
        this.paymentRepository = paymentRepository;
        this.serviceItemRepository = serviceItemRepository;
    }

    protected BarberEntity getBarber(Long id) {
        return barberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Barbeiro não encontrado"));
    }

    protected PaymentEntity getPayment(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));
    }

    protected ServiceItemEntity getService(Long id) {
        return serviceItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));
    }
}
