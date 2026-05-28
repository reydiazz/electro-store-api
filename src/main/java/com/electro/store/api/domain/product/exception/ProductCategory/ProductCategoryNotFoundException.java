package com.electro.store.api.domain.product.exception.ProductCategory;

import com.electro.store.api.shared.exception.BusinessException;

public class ProductCategoryNotFoundException extends BusinessException {

    public ProductCategoryNotFoundException(String code) {
        super("Product category with code " + code + " not found", ProductCategoryErrorCode.PRODUCT_CATEGORY_NOT_FOUND);
    }

}
