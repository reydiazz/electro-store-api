package com.electro.store.api.domain.sales.controller;

import com.electro.store.api.domain.sales.service.SaleService;
import com.electro.store.api.domain.sales.web.request.CreateSaleRequest;
import com.electro.store.api.domain.sales.web.response.SaleResponse;
import com.electro.store.api.domain.sales.web.response.SaleSummaryResponse;
import com.electro.store.api.domain.sales.web.response.SalesDashboardResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/sales")
@RequiredArgsConstructor
public class SaleController {
    private final SaleService service;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SELLER')")
    public ResponseEntity<Page<SaleResponse>> findAll(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String user,
            @RequestParam(required = false) @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME) java.time.LocalDateTime startDate,
            @RequestParam(required = false) @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE_TIME) java.time.LocalDateTime endDate,
            Pageable pageable
    ) {

        Page<SaleResponse> response =
                service.findAll(search, user, startDate, endDate, pageable);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/sellers")
    @PreAuthorize("hasAnyRole('ADMIN', 'SELLER')")
    public ResponseEntity<List<String>> getSellers() {
        return ResponseEntity.ok(service.getDistinctSellers());
    }

    @GetMapping("/dashboard")
    @PreAuthorize("hasAnyRole('ADMIN', 'SELLER')")
    public ResponseEntity<SalesDashboardResponse> getDashboard() {

        SalesDashboardResponse response =
                service.getDashboard();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{code}")
    @PreAuthorize("hasAnyRole('ADMIN', 'SELLER')")
    public ResponseEntity<SaleResponse> findByCode(
            @PathVariable String code
    ) {

        SaleResponse response =
                service.findByCode(code);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{code}/summary")
    @PreAuthorize("hasAnyRole('ADMIN', 'SELLER')")
    public ResponseEntity<SaleSummaryResponse> getSummary(
            @PathVariable String code
    ) {
        SaleSummaryResponse response =
                service.getSummary(code);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'SELLER')")
    public ResponseEntity<SaleResponse> create(
            @Valid @RequestBody CreateSaleRequest request
    ) {

        SaleResponse response =
                service.create(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/daily-summary")
    @PreAuthorize("hasAnyRole('ADMIN', 'SELLER')")
    public ResponseEntity<List<com.electro.store.api.domain.sales.web.response.DailySummaryResponse>> getDailySummary(
            @RequestParam(defaultValue = "7") int days
    ) {
        return ResponseEntity.ok(service.getDailySalesTotals(days));
    }

    @GetMapping("/top-products")
    @PreAuthorize("hasAnyRole('ADMIN', 'SELLER')")
    public ResponseEntity<List<com.electro.store.api.domain.sales.web.response.TopProductResponse>> getTopProducts(
            @RequestParam(defaultValue = "5") int limit
    ) {
        return ResponseEntity.ok(service.getTopSellingProducts(limit));
    }
}

