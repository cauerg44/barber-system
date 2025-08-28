package com.juniorcabellos.barbershop.service;

import com.juniorcabellos.barbershop.dto.BarberResponse;
import com.juniorcabellos.barbershop.entity.BarberEntity;
import com.juniorcabellos.barbershop.repository.BarberRepository;
import com.juniorcabellos.barbershop.service.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BarberService {

    private final BarberRepository repository;

    public BarberService(BarberRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<BarberResponse> findAll() {
        List<BarberEntity> list = repository.findAll();
        return list.stream()
                .map(it -> new BarberResponse(it.getId(), it.getName()))
                .toList();
    }

    @Transactional(readOnly = true)
    public BarberResponse findById(Long id) {
        BarberEntity barberEntity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Barbeiro não encontrado"));
        return new BarberResponse(barberEntity);
    }
}