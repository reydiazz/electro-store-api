package com.electro.store.api.shared.exception;

import org.springframework.http.HttpStatus;

public class BusinessException extends RuntimeException {

    private final ErrorCode code;

    public BusinessException(String message, ErrorCode code) {
        super(message);
        this.code = code;
    }

    public BusinessException(String message, ErrorCode code, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public String getCode() {
        return code.getCode();
    }

    public HttpStatus getHttpStatus() {
        return code.getHttpStatus();
    }

}
