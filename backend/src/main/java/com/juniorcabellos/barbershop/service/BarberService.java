package com.juniorcabellos.barbershop.service;

import com.juniorcabellos.barbershop.dto.BarberResponse;
import com.juniorcabellos.barbershop.entity.BarberEntity;
import com.juniorcabellos.barbershop.repository.BarberRepository;
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
}