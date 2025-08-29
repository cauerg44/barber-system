package com.juniorcabellos.barbershop.service;

import com.juniorcabellos.barbershop.dto.response.ServiceItemResponse;
import com.juniorcabellos.barbershop.entity.ServiceItemEntity;
import com.juniorcabellos.barbershop.repository.ServiceItemRepository;
import com.juniorcabellos.barbershop.service.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ServiceItemService {

    private final ServiceItemRepository repository;

    public ServiceItemService(ServiceItemRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<ServiceItemResponse> findAll() {
        List<ServiceItemEntity> list = repository.findAll();
        return list.stream()
                .map(it -> new ServiceItemResponse(it.getId(), it.getName()))
                .toList();
    }

    @Transactional(readOnly = true)
    public ServiceItemResponse findById(Long id) {
        ServiceItemEntity serviceItemEntity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Serviço do salão não encontrado"));
        return new ServiceItemResponse(serviceItemEntity);
    }
}