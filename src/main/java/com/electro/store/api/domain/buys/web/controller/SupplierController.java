package com.electro.store.api.domain.buys.web.controller;


import com.electro.store.api.domain.buys.component.SupplierMapper;
import com.electro.store.api.domain.buys.model.entity.Supplier;
import com.electro.store.api.domain.buys.service.SupplierService;
import com.electro.store.api.domain.buys.web.request.CreateSupplierRequest;
import com.electro.store.api.domain.buys.web.request.UpdateSupplierRequest;
import com.electro.store.api.domain.buys.web.response.SupplierResponse;
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
@RequestMapping("/api/suppliers")
public class SupplierController {

    private final SupplierService service;
    private final SupplierMapper mapper;

    @GetMapping
    public ResponseEntity<Page<SupplierResponse>> findAll(Pageable pageable) {
        Page<Supplier> page = service.findAll(pageable);
        return ResponseEntity.ok(page.map(mapper::toResponse));
    }

    @PostMapping
    public ResponseEntity<SupplierResponse> create(@RequestBody @Valid CreateSupplierRequest request) {
        Supplier supplier = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(supplier));
    }

    @PutMapping("/{code}")
    public ResponseEntity<SupplierResponse> update(@PathVariable String code, @RequestBody @Valid UpdateSupplierRequest request) {
        Supplier response = service.update(code, request);
        return ResponseEntity.ok(mapper.toResponse(response));
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Void> delete(@PathVariable String code) {
        service.delete(code);
        return ResponseEntity.noContent().build();
    }

}
