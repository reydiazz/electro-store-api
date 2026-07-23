package com.electro.store.api.domain.movement.controller;

import com.electro.store.api.domain.movement.model.enums.GuideType;
import com.electro.store.api.domain.movement.service.InventoryGuideService;
import com.electro.store.api.domain.movement.web.request.CreateInventoryGuideRequest;
import com.electro.store.api.domain.movement.web.response.InventoryGuideResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/inventory-guides")
@RequiredArgsConstructor
public class InventoryGuideController {

    private final InventoryGuideService service;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STOREKEEPER')")
    public ResponseEntity<Page<InventoryGuideResponse>> findAll(
            @RequestParam(required = false) String search,
            @RequestParam(required = false) GuideType type,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            Pageable pageable
    ) {
        Page<InventoryGuideResponse> response = service.findAll(search, type, startDate, endDate, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{code}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STOREKEEPER')")
    public ResponseEntity<InventoryGuideResponse> findByCode(@PathVariable String code) {
        InventoryGuideResponse response = service.findByCode(code);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STOREKEEPER')")
    public ResponseEntity<InventoryGuideResponse> create(
            @Valid @RequestBody CreateInventoryGuideRequest request
    ) {
        InventoryGuideResponse response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
