package com.electro.store.api.domain.auth.exception.auth;

import com.electro.store.api.shared.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum AuthErrorCode implements ErrorCode {

    AUTHENTICATED_PRINCIPAL_NOT_FOUND(HttpStatus.NOT_FOUND),
    NO_AUTHENTICATED_USER(HttpStatus.BAD_REQUEST);

    private final HttpStatus httpStatus;

    AuthErrorCode(HttpStatus httpStatus) {
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
