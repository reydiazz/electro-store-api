package com.electro.store.api.domain.people.exception.customer;

import com.electro.store.api.shared.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum CustomerErrorCode implements ErrorCode {

    CUSTOMER_NOT_FOUND(HttpStatus.NOT_FOUND);

    private final HttpStatus httpStatus;

    CustomerErrorCode(HttpStatus httpStatus){
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
