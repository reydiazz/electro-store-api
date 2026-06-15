package com.electro.store.api.domain.sales.exception;

import com.electro.store.api.shared.exception.BusinessException;

public class SaleNotFoundException extends BusinessException {

    public SaleNotFoundException(String code) {
        super(
                "Sale with code '%s' not found".formatted(code),
                SaleErrorCode.SALE_NOT_FOUND
        );
    }
}
