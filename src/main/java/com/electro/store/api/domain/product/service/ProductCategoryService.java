package com.electro.store.api.domain.product.service;

import com.electro.store.api.domain.product.exception.category.ProductCategoryNotFoundException;
import com.electro.store.api.domain.product.model.entity.ProductCategory;
import com.electro.store.api.domain.product.repository.ProductCategoryRepository;
import com.electro.store.api.domain.product.web.request.CreateProductCategoryRequest;
import com.electro.store.api.domain.product.web.request.UpdateProductCategoryRequest;
import com.electro.store.api.shared.utils.CodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductCategoryService {

    public static final String PREFIX = "CAT";
    private final ProductCategoryRepository repository;

    @Transactional(readOnly = true)
    public Page<ProductCategory> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Transactional
    public ProductCategory create(CreateProductCategoryRequest request) {
        String code = CodeGenerator.next(PREFIX);
        ProductCategory category = new ProductCategory(code, request.name());
        return repository.save(category);
    }

    @Transactional
    public ProductCategory update(String code, UpdateProductCategoryRequest request) {
        ProductCategory category = findByCodeOrThrow(code);
        category.update(request.name());
        return category;
    }

    @Transactional
    public void delete(String code) {
        ProductCategory category = findByCodeOrThrow(code);
        repository.delete(category);
    }

    public ProductCategory findByCodeOrThrow(String code) {
        return repository.findById(code).orElseThrow(
                () -> new ProductCategoryNotFoundException(code)
        );
    }

}
