package com.electro.store.api.domain.product.web.controller;

import com.electro.store.api.domain.product.service.ProductService;
import com.electro.store.api.domain.product.web.request.CreateProductRequest;
import com.electro.store.api.domain.product.web.request.UpdateProductRequest;
import com.electro.store.api.domain.product.web.response.ProductResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.electro.store.api.domain.product.web.response.ProductMetricsResponse;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTION', 'STOREKEEPER')")
    public ResponseEntity<Page<ProductResponse>> findAll(
            @RequestParam(required = false) String search,
            Pageable pageable) {
        Page<ProductResponse> response = service.findAll(search, pageable);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/metrics")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTION', 'STOREKEEPER')")
    public ResponseEntity<ProductMetricsResponse> getMetrics() {
        return ResponseEntity.ok(service.getMetrics());
    }

    @GetMapping("/brands")
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTION', 'STOREKEEPER')")
    public ResponseEntity<List<String>> getBrands() {
        return ResponseEntity.ok(service.findDistinctBrands());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STOREKEEPER')")
    public ResponseEntity<ProductResponse> create(@Valid @RequestBody CreateProductRequest request) {
        ProductResponse response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{code}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STOREKEEPER')")
    public ResponseEntity<ProductResponse> update(@PathVariable String code, @Valid @RequestBody UpdateProductRequest request) {
        ProductResponse response = service.update(code, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{code}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable String code) {
        service.delete(code);
        return ResponseEntity.noContent().build();
    }

}
