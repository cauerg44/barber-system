package com.juniorcabellos.barbershop.controller;

import com.juniorcabellos.barbershop.dto.response.PaymentResponse;
import com.juniorcabellos.barbershop.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/payments")
public class PaymentController {

    private final PaymentService service;

    public PaymentController(PaymentService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<PaymentResponse>> findAllBarbers() {
        var list = service.findAll();
        return ResponseEntity.ok(list);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<PaymentResponse> findById(@PathVariable Long id) {
        var response = service.findById(id);
        return ResponseEntity.ok(response);
    }
}