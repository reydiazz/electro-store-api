package com.electro.store.api.domain.buys.exception;

import com.electro.store.api.shared.exception.BusinessException;

public class PurchaseNotFoundException  extends BusinessException {

    public PurchaseNotFoundException (String code){
        super("Sale with code '%s' not found".formatted(code),
                PurchaseErrorCode.SALE_NOT_FOUND);
    }
}
