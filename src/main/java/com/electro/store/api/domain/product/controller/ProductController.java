package com.electro.store.api.domain.product.controller;

import com.electro.store.api.domain.product.component.ProductMapper;
import com.electro.store.api.domain.product.model.entity.Product;
import com.electro.store.api.domain.product.service.ProductService;
import com.electro.store.api.domain.product.web.request.CreateProductRequest;
import com.electro.store.api.domain.product.web.request.UpdateProductRequest;
import com.electro.store.api.domain.product.web.response.ProductResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService service;
    private final ProductMapper mapper;

    @PostMapping
    public ProductResponse create(
            @Valid @RequestBody CreateProductRequest request
            ) {
        Product product = service.create(request);
        return mapper.toResponse(product);
    }

    @PutMapping("/{code}")
    public ProductResponse update(
            @PathVariable String code,
            @Valid @RequestBody UpdateProductRequest request
            ) {
        Product product = service.update(code, request);
        return mapper.toResponse(product);
    }

    @DeleteMapping("/{code}")
    public void delete(
            @PathVariable String code
    ) {
        service.delete(code);
    }

}
