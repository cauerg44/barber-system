package com.juniorcabellos.barbershop.service;

import com.juniorcabellos.barbershop.dto.request.AppointmentCreateRequest;
import com.juniorcabellos.barbershop.dto.request.AppointmentPatchRequest;
import com.juniorcabellos.barbershop.dto.response.AppointmentResponse;
import com.juniorcabellos.barbershop.entity.AppointmentEntity;
import com.juniorcabellos.barbershop.entity.BarberEntity;
import com.juniorcabellos.barbershop.entity.PaymentEntity;
import com.juniorcabellos.barbershop.entity.ServiceItemEntity;
import com.juniorcabellos.barbershop.entity.enums.AppointmentStatus;
import com.juniorcabellos.barbershop.repository.AppointmentRepository;
import com.juniorcabellos.barbershop.repository.BarberRepository;
import com.juniorcabellos.barbershop.repository.PaymentRepository;
import com.juniorcabellos.barbershop.repository.ServiceItemRepository;
import com.juniorcabellos.barbershop.service.exceptions.DatabaseException;
import com.juniorcabellos.barbershop.service.exceptions.ResourceNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    private final AppointmentRepository repository;

    private final EntityFinder entityFinder;

    public AppointmentService(AppointmentRepository repository, BarberRepository barberRepository, PaymentRepository paymentRepository, ServiceItemRepository serviceItemRepository, EntityFinder entityFinder) {
        this.repository = repository;
        this.entityFinder = entityFinder;
    }

    @Transactional(readOnly = true)
    public List<AppointmentResponse> findAllClientsWaiting() {
        List<AppointmentEntity> list = repository.findClientsWaiting();
        return list.stream()
                .map(appointment -> new AppointmentResponse(appointment))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AppointmentResponse> findAllClientsCutting() {
        List<AppointmentEntity> list = repository.findClientsCutting();
        return list.stream()
                .map(appointment -> new AppointmentResponse(appointment))
                .toList();
    }

    @Transactional
    public AppointmentResponse save(AppointmentCreateRequest request) {
        AppointmentEntity entity = new AppointmentEntity();
        requestToCreate(request, entity);
        repository.save(entity);
        return new AppointmentResponse(entity);
    }

    @Transactional
    public AppointmentResponse patch(Long id, AppointmentPatchRequest request) {

        AppointmentEntity appointment = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));

        if (appointment.getStatus().equals(AppointmentStatus.FINALIZADO)) {
            throw new RuntimeException("Atendimento finalizado não poder ser alterado");
        }

        try {
            AppointmentEntity entity = repository.getReferenceById(id);
            requestToPatch(request, entity);
            entity = repository.save(entity);
            return new AppointmentResponse(entity);
        }
        catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Atendimento não encontrado");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(Long id) {
        AppointmentEntity entity = repository.getReferenceById(id);

        if (!entity.getStatus().equals(AppointmentStatus.AGUARDANDO)) {
            throw new DatabaseException("Só é possível excluir atendimentos em espera");
        }

        try {
            repository.delete(entity);
        }
        catch (DataIntegrityViolationException exception) {
            throw new DatabaseException("Falha de integridade referencial");
        }
    }

    private void requestToCreate(AppointmentCreateRequest request, AppointmentEntity appointment) {
        BarberEntity barber = entityFinder.getBarber(request.barberId());
        PaymentEntity payment = entityFinder.getPayment(request.paymentId());

        appointment.setClientName(request.clientName());
        appointment.setBarber(barber);
        appointment.setPayment(payment);
        appointment.setTotalValue(request.totalValue());

        if (request.servicesIds() != null) {
            request.servicesIds().forEach(id -> {
                ServiceItemEntity service = entityFinder.getService(id);
                appointment.addService(service);
            });
        }
    }

    private void requestToPatch(AppointmentPatchRequest request, AppointmentEntity appointment) {
        Optional.ofNullable(request.clientName())
                .ifPresent(appointment::setClientName);

        Optional.ofNullable(request.barberId())
                .ifPresent(id -> appointment.setBarber(entityFinder.getBarber(id)));

        Optional.ofNullable(request.paymentId())
                .ifPresent(id -> appointment.setPayment(entityFinder.getPayment(id)));

        Optional.ofNullable(request.status())
                        .ifPresent(status -> appointment.setStatus(AppointmentStatus.valueOf(status)));

        Optional.ofNullable(request.totalValue())
                .ifPresent(appointment::setTotalValue);
    }
}