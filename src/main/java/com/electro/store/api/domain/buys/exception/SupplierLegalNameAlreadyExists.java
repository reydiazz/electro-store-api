package com.electro.store.api.domain.buys.exception;

import com.electro.store.api.shared.exception.BusinessException;

public class SupplierLegalNameAlreadyExists extends BusinessException {

    public SupplierLegalNameAlreadyExists(String legalName) {
        super("Supplier with legal name '%s' already exists".formatted(legalName), SupplierErrorCode.SUPPLIER_LEGAL_NAME_ALREADY_EXISTS);
    }

}
