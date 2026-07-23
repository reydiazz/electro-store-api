package com.electro.store.api.domain.sales.exception;

import com.electro.store.api.shared.exception.BusinessException;

public class EmptySaleDetailsException extends BusinessException {

    public EmptySaleDetailsException() {
        super(
                "Sale must contain at least one product",
                SaleErrorCode.SALE_EMPTY_DETAILS
        );
    }
}
