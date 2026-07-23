package com.electro.store.api.domain.people.web.controller;

import com.electro.store.api.domain.people.component.CustomerMapper;
import com.electro.store.api.domain.people.model.entity.Customer;
import com.electro.store.api.domain.people.service.CustomerService;
import com.electro.store.api.domain.people.web.request.CreateCustomerRequest;
import com.electro.store.api.domain.people.web.request.UpdateCustomerRequest;
import com.electro.store.api.domain.people.web.response.CustomerResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.electro.store.api.domain.people.web.response.CustomerMetricsResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService service;
    private final CustomerMapper mapper;

    @GetMapping("/metrics")
    @PreAuthorize("hasAnyRole('ADMIN', 'SELLER')")
    public ResponseEntity<CustomerMetricsResponse> getMetrics() {
        return ResponseEntity.ok(service.getMetrics());
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SELLER')")
    public ResponseEntity<Page<CustomerResponse>> findAll(
            @RequestParam(required = false) String search,
            Pageable pageable
    ) {
        Page<Customer> page = service.findAll(search, pageable);
        return ResponseEntity.ok(page.map(mapper::toResponse));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SELLER')")
    public ResponseEntity<CustomerResponse> create(@RequestBody @Valid CreateCustomerRequest request) {
        Customer customer = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(customer));
    }

    @PutMapping("/{code}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SELLER')")
    public ResponseEntity<CustomerResponse> update(@PathVariable String code, @RequestBody @Valid UpdateCustomerRequest request) {
        Customer customer = service.update(code, request);
        return ResponseEntity.ok(mapper.toResponse(customer));
    }

    @DeleteMapping("/{code}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable String code) {
        service.delete(code);
        return ResponseEntity.noContent().build();
    }

}
