package com.juniorcabellos.barbershop.controller;

import com.juniorcabellos.barbershop.dto.request.AppointmentCreateRequest;
import com.juniorcabellos.barbershop.dto.request.AppointmentPatchRequest;
import com.juniorcabellos.barbershop.dto.response.AppointmentResponse;
import com.juniorcabellos.barbershop.service.AppointmentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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
        var list = service.findAllClientsWaiting();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/cutting")
    public ResponseEntity<List<AppointmentResponse>> getClientsCutting() {
        var list = service.findAllClientsCutting();
        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<AppointmentResponse> saveAppointment(@RequestBody @Valid AppointmentCreateRequest request) {
        var response = service.save(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<AppointmentResponse> patchAppointment(@PathVariable Long id, @RequestBody @Valid AppointmentPatchRequest request) {
        var response = service.patch(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteAppointmentById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}