package com.electro.store.api.domain.buys.exception;

import com.electro.store.api.shared.exception.BusinessException;

public class SupplierPhoneAlreadyExists extends BusinessException {

    public SupplierPhoneAlreadyExists(String phone) {
        super("Supplier with phone '%s' already exists".formatted(phone), SupplierErrorCode.SUPPLIER_PHONE_ALREADY_EXISTS);
    }

}
