package com.electro.store.api.domain.report.exception;

import com.electro.store.api.shared.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum ReportErrorCode implements ErrorCode {

    REPORT_GENERATION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR),
    REPORT_INVALID_YEAR(HttpStatus.BAD_REQUEST);

    private final HttpStatus httpStatus;

    ReportErrorCode(HttpStatus httpStatus) {
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
