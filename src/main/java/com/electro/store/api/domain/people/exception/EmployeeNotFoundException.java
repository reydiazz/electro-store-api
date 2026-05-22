package com.electro.store.api.domain.people.exception;

import com.electro.store.api.shared.exception.BusinessException;

public class EmployeeNotFoundException extends BusinessException {

    public EmployeeNotFoundException(String code) {
        super(
                "Employee with code " + code + " not found",
                EmployeeErrorCode.NOT_FOUND
        );
    }
}