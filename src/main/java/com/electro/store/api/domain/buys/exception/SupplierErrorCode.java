package com.electro.store.api.domain.buys.exception;

import com.electro.store.api.shared.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum SupplierErrorCode  implements ErrorCode {
    SUPPLIER_ERROR_CODE(HttpStatus.NOT_FOUND);

    private final HttpStatus httpStatus;

    SupplierErrorCode(HttpStatus httpStatus) {
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
