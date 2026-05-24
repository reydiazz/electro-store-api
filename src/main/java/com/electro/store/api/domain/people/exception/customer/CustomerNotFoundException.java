package com.electro.store.api.domain.people.exception.customer;

import com.electro.store.api.shared.exception.BusinessException;

public class CustomerNotFoundException extends BusinessException {

    public CustomerNotFoundException(String code) {
        super("Customer with code " + code + " not found", CustomerErrorCode.CUSTOMER_NOT_FOUND);
    }

}
