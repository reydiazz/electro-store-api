package com.electro.store.api.domain.product.controller;

import com.electro.store.api.domain.product.component.ProductCategoryMapper;
import com.electro.store.api.domain.product.model.entity.ProductCategory;
import com.electro.store.api.domain.product.service.ProductCategoryService;
import com.electro.store.api.domain.product.web.request.CreateProductCategoryRequest;
import com.electro.store.api.domain.product.web.request.UpdateProductCategoryRequest;
import com.electro.store.api.domain.product.web.response.ProductCategoryResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("product-categories")
@RequiredArgsConstructor
public class ProductCategoryController {
    private final ProductCategoryService service;
    private final ProductCategoryMapper mapper;

    @PostMapping
    public ProductCategoryResponse create(
            @Valid @RequestBody CreateProductCategoryRequest request
            ){
        ProductCategory category = service.create(request);
        return mapper.toResponse(category);
    }

    @PutMapping("/{code}")
    public ProductCategoryResponse update(
            @PathVariable String code,
            @Valid @RequestBody UpdateProductCategoryRequest request
            ) {
        ProductCategory category = service.update(code, request);
        return mapper.toResponse(category);
    }

    @DeleteMapping("/{code}")
    public void delete(
            @PathVariable String code
    ) {
        service.delete(code);
    }
}
