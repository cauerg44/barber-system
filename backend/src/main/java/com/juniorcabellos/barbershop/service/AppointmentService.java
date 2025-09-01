package com.juniorcabellos.barbershop.service;

import com.juniorcabellos.barbershop.dto.request.AppointmentCreateRequest;
import com.juniorcabellos.barbershop.dto.request.AppointmentUpdateRequest;
import com.juniorcabellos.barbershop.dto.response.AppointmentResponse;
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

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final BarberRepository barberRepository;
    private final PaymentRepository paymentRepository;
    private final ServiceItemRepository serviceItemRepository;

    private final EntityFinder entityFinder;

    public AppointmentService(AppointmentRepository appointmentRepository, BarberRepository barberRepository, PaymentRepository paymentRepository, ServiceItemRepository serviceItemRepository, EntityFinder entityFinder) {
        this.appointmentRepository = appointmentRepository;
        this.barberRepository = barberRepository;
        this.paymentRepository = paymentRepository;
        this.serviceItemRepository = serviceItemRepository;
        this.entityFinder = entityFinder;
    }

    @Transactional(readOnly = true)
    public List<AppointmentResponse> findAllClientsWaiting() {
        List<AppointmentEntity> list = appointmentRepository.findClientsWaiting();
        return list.stream()
                .map(AppointmentResponse::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AppointmentResponse> findAllClientsCutting() {
        List<AppointmentEntity> list = appointmentRepository.findClientsCutting();
        return list.stream()
                .map(AppointmentResponse::new)
                .toList();
    }

    @Transactional
    public void save(AppointmentCreateRequest request) {

        AppointmentEntity appointmentEntity = requestToEntity(request);

        appointmentRepository.save(appointmentEntity);
    }

    @Transactional
    public void update(Long id, AppointmentUpdateRequest request) {

        AppointmentEntity appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));

        switch (appointment.getStatus()) {
            case AGUARDANDO -> updateWhenWaiting(appointment, request);
            case CORTANDO -> updateWhenCutting(appointment, request);
            case FINALIZADO -> throw new RuntimeException("Agendamento finalizado não pode ser alterado");
        }

        appointmentRepository.save(appointment);
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

    private void updateWhenWaiting(AppointmentEntity appointment, AppointmentUpdateRequest request) {
        Optional.ofNullable(request.barberId())
                .ifPresent(barberId -> appointment.setBarber(entityFinder.getBarber(barberId)));

        Optional.ofNullable(request.paymentId())
                .ifPresent(paymentId -> appointment.setPayment(entityFinder.getPayment(paymentId)));

        Optional.ofNullable(request.totalValue())
                .ifPresent(appointment::setTotalValue);
    }

    private void updateWhenCutting(AppointmentEntity appointment, AppointmentUpdateRequest request) {
        if (request.serviceIds() != null && !request.serviceIds().isEmpty()) {
            appointment.getServices().clear();
            request.serviceIds()
                    .forEach(serviceId -> appointment.addService(entityFinder.getService(serviceId)));
        }

        Optional.ofNullable(request.totalValue())
                .ifPresent(appointment::setTotalValue);
    }
}