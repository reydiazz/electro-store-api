package com.electro.store.api.domain.buys.exception;

import com.electro.store.api.shared.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum PurchaseErrorCode  implements ErrorCode {
    SALE_NOT_FOUND(HttpStatus.NOT_FOUND),
    SALE_EMPTY_DETAILS(HttpStatus.BAD_REQUEST);

    private final HttpStatus httpStatus;

    PurchaseErrorCode(HttpStatus httpStatus) {
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

