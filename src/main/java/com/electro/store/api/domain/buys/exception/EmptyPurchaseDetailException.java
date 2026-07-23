package com.electro.store.api.domain.buys.exception;

import com.electro.store.api.shared.exception.BusinessException;

public class EmptyPurchaseDetailException extends BusinessException {
    public EmptyPurchaseDetailException (){
        super("Sale must contain at least one product",
                PurchaseErrorCode.SALE_EMPTY_DETAILS);
    }
}
