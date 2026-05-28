package com.electro.store.api.domain.product.service;

import com.electro.store.api.domain.product.component.ProductMapper;
import com.electro.store.api.domain.product.exception.Product.ProductNotFoundException;
import com.electro.store.api.domain.product.model.entity.Product;
import com.electro.store.api.domain.product.model.entity.ProductCategory;
import com.electro.store.api.domain.product.repository.ProductRepository;
import com.electro.store.api.domain.product.web.request.CreateProductRequest;
import com.electro.store.api.domain.product.web.request.UpdateProductRequest;
import com.electro.store.api.domain.product.web.response.ProductResponse;
import com.electro.store.api.shared.utils.CodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    public static final String PREFIX = "PRD";
    private final ProductRepository repository;
    private final ProductMapper mapper;

    private final ProductCategoryService categoryService;

    @Transactional
    public Page<ProductResponse> findAll(Pageable pageable) {
        Page<Product> products = repository.findAll(pageable);
        return products.map(mapper::toResponse);
    }

    @Transactional
    public ProductResponse create(CreateProductRequest request) {
        String code = CodeGenerator.next(PREFIX);
        ProductCategory category = categoryService.findByCodeOrThrow(request.categoryCode());
        Product product = new Product(code, category, request.name(), request.brand(), request.model(), request.salePrice(), request.description(), request.warrantyMonths());
        Product saved = repository.save(product);
        return mapper.toResponse(saved);
    }

    @Transactional
    public ProductResponse update(String code, UpdateProductRequest request) {
        Product product = findByCodeOrThrow(code);
        ProductCategory category = categoryService.findByCodeOrThrow(request.categoryCode());
        product.update(category, request.name(), request.brand(), request.model(), request.salePrice(), request.description(), request.warrantyMonths());
        return mapper.toResponse(product);
    }

    @Transactional
    public void delete(String code) {
        Product product = findByCodeOrThrow(code);
        repository.delete(product);
    }

    public Product findByCodeOrThrow(String code) {
        return repository.findById(code).orElseThrow(
                () -> new ProductNotFoundException(code)
        );
    }

}
