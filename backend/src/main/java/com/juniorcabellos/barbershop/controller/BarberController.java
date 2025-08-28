package com.juniorcabellos.barbershop.controller;

import com.juniorcabellos.barbershop.dto.BarberResponse;
import com.juniorcabellos.barbershop.service.BarberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/barbers")
public class BarberController {

    private final BarberService service;

    public BarberController(BarberService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<BarberResponse>> findAllBarbers() {
        var list = service.findAll();
        return ResponseEntity.ok(list);
    }
}