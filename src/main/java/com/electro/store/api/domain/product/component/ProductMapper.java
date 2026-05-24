package com.electro.store.api.domain.product.component;

import com.electro.store.api.domain.product.model.entity.Product;
import com.electro.store.api.domain.product.web.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductMapper {
    private final ProductCategoryMapper productCategoryMapper;

    public ProductResponse toResponse(Product product){
        return new ProductResponse(
                product.getCode(),
                productCategoryMapper.toResponse(product.getCategory()),
                product.getName(),
                product.getBrand(),
                product.getModel(),
                product.getSalePrice(),
                product.getStock(),
                product.getDescription(),
                product.getWarrantyMonths()
        );
    }

}
