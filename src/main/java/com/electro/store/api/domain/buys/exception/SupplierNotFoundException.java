package com.electro.store.api.domain.buys.exception;

import com.electro.store.api.domain.people.exception.customer.CustomerErrorCode;
import com.electro.store.api.shared.exception.BusinessException;

public class SupplierNotFoundException extends BusinessException {
    public SupplierNotFoundException(String code) {
        super("Customer with code " + code + " not found", SupplierErrorCode.SUPPLIER_ERROR_CODE);
    }
}
