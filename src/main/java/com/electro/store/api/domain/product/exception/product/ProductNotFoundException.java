package com.electro.store.api.domain.product.exception.product;

import com.electro.store.api.shared.exception.BusinessException;

public class ProductNotFoundException extends BusinessException {

    public ProductNotFoundException(String code) {
        super("Product with code '%s' not found".formatted(code), ProductErrorCode.PRODUCT_NOT_FOUND);
    }

}
