package com.electro.store.api.domain.people.web.controller;

import com.electro.store.api.domain.people.component.EmployeeMapper;
import com.electro.store.api.domain.people.model.entity.Employee;
import com.electro.store.api.domain.people.service.EmployeeService;
import com.electro.store.api.domain.people.web.request.CreateEmployeeRequest;
import com.electro.store.api.domain.people.web.request.UpdateEmployeeRequest;
import com.electro.store.api.domain.people.web.response.EmployeeResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN')")
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService service;
    private final EmployeeMapper mapper;

    @GetMapping
    public ResponseEntity<Page<EmployeeResponse>> findAll(Pageable pageable) {
        Page<Employee> page = service.findAll(pageable);
        return ResponseEntity.ok(page.map(mapper::toResponse));
    }

    @PostMapping
    public ResponseEntity<EmployeeResponse> create(@RequestBody @Valid CreateEmployeeRequest request) {
        Employee employee = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(employee));
    }

    @PutMapping("/{code}")
    public ResponseEntity<EmployeeResponse> update(@PathVariable String code, @RequestBody @Valid UpdateEmployeeRequest request) {
        Employee employee = service.update(code, request);
        return ResponseEntity.ok(mapper.toResponse(employee));
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Void> delete(@PathVariable String code) {
        service.delete(code);
        return ResponseEntity.noContent().build();
    }

}