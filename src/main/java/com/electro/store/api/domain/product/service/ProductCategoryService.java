package com.electro.store.api.domain.product.service;

import com.electro.store.api.domain.product.exception.ProductCategory.ProductCategoryNotFoundException;
import com.electro.store.api.domain.product.model.entity.ProductCategory;
import com.electro.store.api.domain.product.repository.ProductCategoryRepository;
import com.electro.store.api.domain.product.web.request.CreateProductCategoryRequest;
import com.electro.store.api.domain.product.web.request.UpdateProductCategoryRequest;
import com.electro.store.api.shared.utils.CodeGenerator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductCategoryService {
    public static final String PREFIX = "CAT";

    private final ProductCategoryRepository repository;

    @Transactional
    public ProductCategory create(CreateProductCategoryRequest request){
        String code = CodeGenerator.next(PREFIX);
        ProductCategory category = new ProductCategory(
                code,
                request.name()
        );
        return repository.save(category);
    }

    @Transactional
    public ProductCategory update(String code, UpdateProductCategoryRequest request) {
        ProductCategory category = findByCodeOrThrow(code);
        category.update(
                request.name()
        );
        return category;
    }

    @Transactional
    public void delete(String code){
        ProductCategory category = findByCodeOrThrow(code);
        repository.delete(category);
    }

    public ProductCategory findByCodeOrThrow(String code){
        return repository.findById(code).orElseThrow(
                () -> new ProductCategoryNotFoundException(code)
        );
    }
}
