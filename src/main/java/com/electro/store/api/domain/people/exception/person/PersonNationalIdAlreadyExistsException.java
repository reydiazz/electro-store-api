package com.electro.store.api.domain.people.exception.person;

import com.electro.store.api.shared.exception.BusinessException;

public class PersonNationalIdAlreadyExistsException extends BusinessException {

    public PersonNationalIdAlreadyExistsException(String nationalId) {
        super("Person with national id '%s' already exists".formatted(nationalId), PersonErrorCode.PERSON_NATIONAL_ID_ALREADY_EXISTS);
    }

}
