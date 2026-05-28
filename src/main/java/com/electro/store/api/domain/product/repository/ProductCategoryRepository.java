package com.electro.store.api.domain.product.repository;

import com.electro.store.api.domain.product.model.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, String> {
}
