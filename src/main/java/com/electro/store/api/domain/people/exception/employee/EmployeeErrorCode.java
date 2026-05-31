package com.electro.store.api.domain.people.exception.employee;

import com.electro.store.api.shared.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum EmployeeErrorCode implements ErrorCode {

    EMPLOYEE_NOT_FOUND(HttpStatus.NOT_FOUND),
    EMPLOYEE_POSITION_HAS_NO_SYSTEM_ROLE(HttpStatus.CONFLICT);

    private final HttpStatus httpStatus;

    EmployeeErrorCode(HttpStatus httpStatus) {
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
