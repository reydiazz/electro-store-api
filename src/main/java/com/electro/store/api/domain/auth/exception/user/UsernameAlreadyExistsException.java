package com.electro.store.api.domain.people.exception.customer;

import com.electro.store.api.shared.exception.BusinessException;

public class CustomerTaxIdAlreadyExistsException extends BusinessException {

    public CustomerTaxIdAlreadyExistsException(String taxId) {
        super("Customer with tax id '%s' already exists".formatted(taxId), CustomerErrorCode.CUSTOMER_TAX_ID_ALREADY_EXISTS);
    }

}
