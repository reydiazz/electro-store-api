package com.electro.store.api.domain.product.controller;

import com.electro.store.api.domain.product.service.ProductCategoryService;
import com.electro.store.api.domain.product.web.request.CreateProductCategoryRequest;
import com.electro.store.api.domain.product.web.request.UpdateProductCategoryRequest;
import com.electro.store.api.domain.product.web.response.ProductCategoryResponse;
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
@RequestMapping("/api/product-categories")
public class ProductCategoryController {

    private final ProductCategoryService service;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'RECEPTION', 'STOREKEEPER')")
    public ResponseEntity<Page<ProductCategoryResponse>> findAll(Pageable pageable) {
        Page<ProductCategoryResponse> response = service.findAll(pageable);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductCategoryResponse> create(@Valid @RequestBody CreateProductCategoryRequest request) {
        ProductCategoryResponse response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{code}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ProductCategoryResponse> update(@PathVariable String code, @Valid @RequestBody UpdateProductCategoryRequest request) {
        ProductCategoryResponse response = service.update(code, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{code}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable String code) {
        service.delete(code);
        return ResponseEntity.noContent().build();
    }

}

