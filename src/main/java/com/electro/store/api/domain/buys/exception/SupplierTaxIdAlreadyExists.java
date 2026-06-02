package com.electro.store.api.domain.buys.exception;

import com.electro.store.api.shared.exception.BusinessException;

public class SupplierTaxIdAlreadyExists extends BusinessException {

    public SupplierTaxIdAlreadyExists(String taxId) {
        super("Supplier with tax id '%s' already exists".formatted(taxId), SupplierErrorCode.SUPPLIER_TAX_ID_ALREADY_EXISTS);
    }

}
