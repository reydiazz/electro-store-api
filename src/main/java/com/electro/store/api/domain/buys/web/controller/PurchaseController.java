package com.electro.store.api.domain.buys.web.controller;

import com.electro.store.api.domain.buys.service.PurchaseService;
import com.electro.store.api.domain.buys.web.request.CreatePurchaseRequest;
import com.electro.store.api.domain.buys.web.response.PurchaseDashboardResponse;
import com.electro.store.api.domain.buys.web.response.PurchaseSummaryResponse;
import com.electro.store.api.domain.buys.web.response.PurchasesResponse;
import com.electro.store.api.domain.buys.web.response.PurchaseMetricsResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/purchases")
@RequiredArgsConstructor
public class PurchaseController {

    private final PurchaseService service;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTION')")
    public ResponseEntity<Page<PurchasesResponse>> findAll(
            @RequestParam(required = false) String search,
            Pageable pageable){
         Page<PurchasesResponse> response = service.findAll(search, pageable);

         return  ResponseEntity.ok(response);
    }
    @GetMapping("/dashboard")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTION')")
    public  ResponseEntity<PurchaseDashboardResponse> getDashboard (){
        PurchaseDashboardResponse response = service.getDashboard();
        return  ResponseEntity.ok(response);
    }

    @GetMapping("/{code}/summary")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTION')")
    public ResponseEntity<PurchaseSummaryResponse> getSummary (@PathVariable String code){
        PurchaseSummaryResponse response = service.getSummary(code);
        return  ResponseEntity.ok(response);
    }

    @GetMapping("/metrics")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTION')")
    public ResponseEntity<PurchaseMetricsResponse> getMetrics() {
        return ResponseEntity.ok(service.getMetrics());
    }

    @GetMapping("/{code}")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTION')")
    public ResponseEntity<PurchasesResponse> findByCode(
            @PathVariable String code
    ) {
        PurchasesResponse response = service.findByCode(code);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTION')")
    public  ResponseEntity<PurchasesResponse> create(@Valid @RequestBody CreatePurchaseRequest request){
        PurchasesResponse response = service.create(request);

        return  ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
