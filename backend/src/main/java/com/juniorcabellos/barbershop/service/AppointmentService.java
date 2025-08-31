package com.juniorcabellos.barbershop.service;

import com.juniorcabellos.barbershop.dto.request.AppointmentCreateRequest;
import com.juniorcabellos.barbershop.entity.AppointmentEntity;
import com.juniorcabellos.barbershop.entity.BarberEntity;
import com.juniorcabellos.barbershop.entity.PaymentEntity;
import com.juniorcabellos.barbershop.entity.ServiceItemEntity;
import com.juniorcabellos.barbershop.repository.AppointmentRepository;
import com.juniorcabellos.barbershop.repository.BarberRepository;
import com.juniorcabellos.barbershop.repository.PaymentRepository;
import com.juniorcabellos.barbershop.repository.ServiceItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final BarberRepository barberRepository;
    private final PaymentRepository paymentRepository;
    private final ServiceItemRepository serviceItemRepository;

    public AppointmentService(AppointmentRepository appointmentRepository, BarberRepository barberRepository, PaymentRepository paymentRepository, ServiceItemRepository serviceItemRepository) {
        this.appointmentRepository = appointmentRepository;
        this.barberRepository = barberRepository;
        this.paymentRepository = paymentRepository;
        this.serviceItemRepository = serviceItemRepository;
    }

    @Transactional
    public void save(AppointmentCreateRequest request) {

        AppointmentEntity appointmentEntity = requestToEntity(request);

        appointmentRepository.save(appointmentEntity);
    }

    private AppointmentEntity requestToEntity(AppointmentCreateRequest request) {
        BarberEntity barber = barberRepository.findById(request.barberId())
                .orElseThrow(() -> new RuntimeException("Barbeiro não encontrado"));

        PaymentEntity payment = paymentRepository.findById(request.paymentId())
                .orElseThrow(() -> new RuntimeException("Pagamento não encontrado"));

        AppointmentEntity appointment = new AppointmentEntity();
        appointment.setClientName(request.clientName());
        appointment.setBarber(barber);
        appointment.setPayment(payment);
        appointment.setTotalValue(request.totalValue());

        if (request.servicesIds() != null) {
            request.servicesIds().forEach(id -> {
                ServiceItemEntity service = serviceItemRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Serviço não encontrado: " + id));
                appointment.addService(service);
            });
        }

        return appointment;
    }
}
