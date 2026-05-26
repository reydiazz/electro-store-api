package com.electro.store.api.domain.people.web.controller;

import com.electro.store.api.domain.people.service.CustomerService;
import com.electro.store.api.domain.people.web.request.CreateCustomerRequest;
import com.electro.store.api.domain.people.web.request.UpdateCustomerRequest;
import com.electro.store.api.domain.people.web.response.CustomerResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN','RECEPTION')")
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService service;

    @PostMapping
    public ResponseEntity<CustomerResponse> create(@RequestBody @Valid CreateCustomerRequest request) {
        CustomerResponse response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{code}")
    public ResponseEntity<CustomerResponse> update(@PathVariable String code, @RequestBody @Valid UpdateCustomerRequest request) {
        CustomerResponse response = service.update(code, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<CustomerResponse> delete(@PathVariable String code) {
        service.delete(code);
        return ResponseEntity.noContent().build();
    }


}
