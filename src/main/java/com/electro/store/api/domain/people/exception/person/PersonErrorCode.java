package com.electro.store.api.domain.people.exception.person;

import com.electro.store.api.shared.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum PersonErrorCode implements ErrorCode {

    PERSON_NOT_FOUND(HttpStatus.NOT_FOUND),
    PERSON_NATIONAL_ID_ALREADY_EXISTS(HttpStatus.CONFLICT);

    private final HttpStatus httpStatus;

    PersonErrorCode(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    @Override
    public String getCode() {
        return name();
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}
