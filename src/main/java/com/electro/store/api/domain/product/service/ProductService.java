package com.electro.store.api.domain.product.service;

import com.electro.store.api.domain.product.exception.Product.ProductNotFoundException;
import com.electro.store.api.domain.product.model.entity.Product;
import com.electro.store.api.domain.product.model.entity.ProductCategory;
import com.electro.store.api.domain.product.repository.ProductRepository;
import com.electro.store.api.domain.product.web.request.CreateProductRequest;
import com.electro.store.api.domain.product.web.request.UpdateProductRequest;
import com.electro.store.api.shared.utils.CodeGenerator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {
    public static final String PREFIX = "PRD";
    private final ProductRepository repository;
    private final ProductCategoryService productCategoryService;

    @Transactional
    public Product create(CreateProductRequest request) {
        String code = CodeGenerator.next(PREFIX);
        ProductCategory category =
                productCategoryService.findByCodeOrThrow(
                        request.categoryCode()
                        );
        Product product = new Product(
                code,
                category,
                request.name(),
                request.brand(),
                request.model(),
                request.salePrice(),
                request.description(),
                request.warrantyMonths()
        );
        return repository.save(product);
    }

    @Transactional
    public Product update(String code, UpdateProductRequest request){
        Product product = findByCodeOrThrow(code);
        ProductCategory category =
                productCategoryService.findByCodeOrThrow(
                        request.categoryCode()
                );
        product.update(
                category,
                request.name(),
                request.brand(),
                request.model(),
                request.salePrice(),
                request.description(),
                request.warrantyMonths()
        );
        return product;
    }

    @Transactional
    public void delete(String code){
        Product product = findByCodeOrThrow(code);
        repository.delete(product);
    }

    public Product findByCodeOrThrow(String code){
        return  repository.findById(code).orElseThrow(
                () -> new ProductNotFoundException(code)
        );
    }
}
