package com.electro.store.api.domain.people.exception.person;

import com.electro.store.api.shared.exception.BusinessException;

public class PersonNotFoundException extends BusinessException {

    public PersonNotFoundException(String code) {
        super("Person with code '%s' not found".formatted(code), PersonErrorCode.PERSON_NOT_FOUND);
    }

}
