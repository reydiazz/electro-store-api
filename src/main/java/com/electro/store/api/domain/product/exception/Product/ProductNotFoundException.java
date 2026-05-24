package com.electro.store.api.domain.product.exception.Product;

import com.electro.store.api.shared.exception.BusinessException;

public class ProductNotFoundException extends BusinessException {

    public ProductNotFoundException(String code){
        super(
                "Product with code " + code + " not found",
                ProductErrorCode.PRODUCT_NOT_FOUND
        );
    }
}
