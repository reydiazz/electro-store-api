package com.electro.store.api.domain.buys.web.controller;

import com.electro.store.api.domain.buys.service.PurchaseService;
import com.electro.store.api.domain.buys.web.request.CreatePurchaseRequest;
import com.electro.store.api.domain.buys.web.response.PurchasesResponse;
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
    public ResponseEntity<Page<PurchasesResponse>> findAll(Pageable pageable){
         Page<PurchasesResponse> response = service.findAll(pageable);

         return  ResponseEntity.ok(response);
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
