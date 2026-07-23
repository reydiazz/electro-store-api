package com.electro.store.api.domain.product.service;

import com.electro.store.api.domain.product.component.ProductMapper;
import com.electro.store.api.domain.product.exception.product.ProductNotFoundException;
import com.electro.store.api.domain.product.model.entity.Product;
import com.electro.store.api.domain.product.model.entity.ProductCategory;
import com.electro.store.api.domain.product.repository.ProductRepository;
import com.electro.store.api.domain.product.web.request.CreateProductRequest;
import com.electro.store.api.domain.product.web.request.UpdateProductRequest;
import com.electro.store.api.domain.product.web.response.ProductMetricsResponse;
import com.electro.store.api.domain.product.web.response.ProductResponse;
import com.electro.store.api.shared.utils.CodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    public static final String PREFIX = "PRD";
    private final ProductRepository repository;
    private final ProductMapper mapper;

    private final ProductCategoryService categoryService;

    @Transactional(readOnly = true)
    public Page<ProductResponse> findAll(String search, Pageable pageable) {
        return findAll(search, null, null, null, pageable);
    }

    @Transactional(readOnly = true)
    public Page<ProductResponse> findAll(String search, String categoryName, String brand, String stockStatus, Pageable pageable) {
        String cleanSearch = (search != null && !search.trim().isEmpty()) ? search.trim() : null;
        String cleanCategory = (categoryName != null && !categoryName.trim().isEmpty() && !"Todas".equalsIgnoreCase(categoryName.trim()) && !"Categoría".equalsIgnoreCase(categoryName.trim())) ? categoryName.trim() : null;
        String cleanBrand = (brand != null && !brand.trim().isEmpty() && !"Todas".equalsIgnoreCase(brand.trim()) && !"Marca".equalsIgnoreCase(brand.trim())) ? brand.trim() : null;
        String cleanStockStatus = (stockStatus != null && !stockStatus.trim().isEmpty() && !"Todos".equalsIgnoreCase(stockStatus.trim())) ? stockStatus.trim() : null;

        Page<Product> products = repository.searchWithFilters(cleanSearch, cleanCategory, cleanBrand, cleanStockStatus, pageable);
        return products.map(mapper::toResponse);
    }

    @Transactional
    public ProductResponse create(CreateProductRequest request) {
        String code = CodeGenerator.next(PREFIX);
        ProductCategory category = categoryService.findByCodeOrThrow(request.categoryCode());
        Product product = new Product(code, category, request.name(), request.brand(), request.model(), request.salePrice(), request.description(), request.warrantyMonths(), request.lowStock());
        Product saved = repository.save(product);
        return mapper.toResponse(saved);
    }

    @Transactional
    public ProductResponse update(String code, UpdateProductRequest request) {
        Product product = findByCodeOrThrow(code);
        ProductCategory category = categoryService.findByCodeOrThrow(request.categoryCode());
        product.update(category, request.name(), request.brand(), request.model(), request.salePrice(), request.description(), request.warrantyMonths(), request.lowStock());
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

    @Transactional(readOnly = true)
    public ProductMetricsResponse getMetrics() {
        long totalProducts = repository.count();
        long lowStockCount = repository.countLowStock();
        long outOfStockCount = repository.countOutOfStock();
        long totalCategories = repository.countDistinctCategories();
        return new ProductMetricsResponse(totalProducts, lowStockCount, outOfStockCount, totalCategories);
    }

    @Transactional(readOnly = true)
    public List<String> findDistinctBrands() {
        return repository.findDistinctBrands();
    }

    @Transactional(readOnly = true)
    public List<com.electro.store.api.domain.product.web.response.CategoryDistributionResponse> getCategoryDistribution() {
        List<Object[]> results = repository.countByCategory();
        return results.stream()
                .map(row -> new com.electro.store.api.domain.product.web.response.CategoryDistributionResponse((String) row[0], ((Number) row[1]).longValue()))
                .collect(Collectors.toList());
    }
}
