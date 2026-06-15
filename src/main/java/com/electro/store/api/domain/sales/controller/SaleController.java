package com.electro.store.api.domain.sales.controller;

import com.electro.store.api.domain.sales.service.SaleService;
import com.electro.store.api.domain.sales.web.request.CreateSaleRequest;
import com.electro.store.api.domain.sales.web.response.SaleResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sales")
@RequiredArgsConstructor
public class SaleController {
    private final SaleService service;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTION')")
    public ResponseEntity<Page<SaleResponse>> findAll(Pageable pageable) {

        Page<SaleResponse> response =
                service.findAll(pageable);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{code}")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTION')")
    public ResponseEntity<SaleResponse> findByCode(
            @PathVariable String code
    ) {

        SaleResponse response =
                service.findByCode(code);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTION')")
    public ResponseEntity<SaleResponse> create(
            @Valid @RequestBody CreateSaleRequest request
    ) {

        SaleResponse response =
                service.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }
}

