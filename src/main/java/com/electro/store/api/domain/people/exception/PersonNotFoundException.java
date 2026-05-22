package com.electro.store.api.domain.people.exception;

import com.electro.store.api.shared.exception.BusinessException;

public class PersonNotFoundException extends BusinessException {

    public PersonNotFoundException(String code) {
        super(
                "Person with code " + code + " not found",
                PersonErrorCode.NOT_FOUND
        );
    }
}