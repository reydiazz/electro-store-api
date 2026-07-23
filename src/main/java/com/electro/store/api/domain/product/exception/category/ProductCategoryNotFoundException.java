package com.electro.store.api.domain.product.exception.category;

import com.electro.store.api.shared.exception.BusinessException;

public class ProductCategoryNotFoundException extends BusinessException {

    public ProductCategoryNotFoundException(String code) {
        super("Product category with code '%s' not found".formatted(code), ProductCategoryErrorCode.PRODUCT_CATEGORY_NOT_FOUND);
    }

}
