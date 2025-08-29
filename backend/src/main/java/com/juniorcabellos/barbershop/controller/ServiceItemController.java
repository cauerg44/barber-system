package com.juniorcabellos.barbershop.controller;

import com.juniorcabellos.barbershop.dto.response.BarberResponse;
import com.juniorcabellos.barbershop.dto.response.ServiceItemResponse;
import com.juniorcabellos.barbershop.service.ServiceItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/services")
public class ServiceItemController {

    private final ServiceItemService service;

    public ServiceItemController(ServiceItemService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ServiceItemResponse>> findAllBarbers() {
        var list = service.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ServiceItemResponse> findById(@PathVariable Long id) {
        var response = service.findById(id);
        return ResponseEntity.ok(response);
    }
}