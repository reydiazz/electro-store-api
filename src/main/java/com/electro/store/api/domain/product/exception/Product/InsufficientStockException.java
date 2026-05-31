package com.electro.store.api.domain.product.exception.Product;

import com.electro.store.api.shared.exception.BusinessException;

public class InsufficientStockException extends BusinessException {

    public InsufficientStockException(String code) {
        super(
                "Product with code " + code + " does not have enough stock",
                ProductErrorCode.INSUFFICIENT_STOCK
        );
    }
}
