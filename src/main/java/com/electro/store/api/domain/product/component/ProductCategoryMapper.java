package com.electro.store.api.domain.product.component;

import com.electro.store.api.domain.product.model.entity.ProductCategory;
import com.electro.store.api.domain.product.web.response.ProductCategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class ProductCategoryMapper {
    public ProductCategoryResponse toResponse(ProductCategory category){
        return new ProductCategoryResponse(
                category.getCode(),
                category.getName()
        );
    }
}
