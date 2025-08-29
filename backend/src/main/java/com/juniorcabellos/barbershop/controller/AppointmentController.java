package com.juniorcabellos.barbershop.controller;

import com.juniorcabellos.barbershop.dto.request.AppointmentCreateRequest;
import com.juniorcabellos.barbershop.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/appointments")
public class AppointmentController {

    private final AppointmentService service;

    public AppointmentController(AppointmentService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> saveAppointment(@RequestBody @Valid AppointmentCreateRequest request) {
        service.save(request);
        return ResponseEntity.created(null).build();
    }
}