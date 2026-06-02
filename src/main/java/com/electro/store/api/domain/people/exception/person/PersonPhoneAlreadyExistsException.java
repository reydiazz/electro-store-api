package com.electro.store.api.domain.people.exception.person;

import com.electro.store.api.shared.exception.BusinessException;

public class PersonPhoneAlreadyExistsException extends BusinessException {

    public PersonPhoneAlreadyExistsException(String phone) {
        super("Person with phone '%s' already exists".formatted(phone), PersonErrorCode.PERSON_PHONE_ALREADY_EXISTS);
    }

}
