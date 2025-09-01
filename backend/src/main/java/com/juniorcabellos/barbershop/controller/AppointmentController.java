package com.juniorcabellos.barbershop.controller;

import com.juniorcabellos.barbershop.dto.request.AppointmentCreateRequest;
import com.juniorcabellos.barbershop.dto.request.AppointmentUpdateRequest;
import com.juniorcabellos.barbershop.dto.response.AppointmentResponse;
import com.juniorcabellos.barbershop.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/appointments")
public class AppointmentController {

    private final AppointmentService service;

    public AppointmentController(AppointmentService service) {
        this.service = service;
    }

    @GetMapping("/waiting")
    public ResponseEntity<List<AppointmentResponse>> getClientsWaiting() {
        var result = service.findAllClientsWaiting();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/cutting")
    public ResponseEntity<List<AppointmentResponse>> getClientsCutting() {
        var result = service.findAllClientsCutting();
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<Void> saveAppointment(@RequestBody @Valid AppointmentCreateRequest request) {
        service.save(request);
        return ResponseEntity.created(null).build();
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<Void> updateAppointment(@PathVariable Long id, @RequestBody @Valid AppointmentUpdateRequest request) {
        service.update(id, request);
        return ResponseEntity.noContent().build();
    }
}