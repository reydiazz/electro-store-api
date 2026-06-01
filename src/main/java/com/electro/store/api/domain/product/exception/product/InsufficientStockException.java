package com.electro.store.api.domain.product.exception.product;

import com.electro.store.api.shared.exception.BusinessException;

public class InsufficientStockException extends BusinessException {

    public InsufficientStockException(String code) {
        super("Product with code '%s' does not have enough stock".formatted(code), ProductErrorCode.PRODUCT_INSUFFICIENT_STOCK);
    }

}
