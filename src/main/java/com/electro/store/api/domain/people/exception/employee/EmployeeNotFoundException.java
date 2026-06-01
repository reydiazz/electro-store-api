package com.electro.store.api.domain.people.exception.employee;

import com.electro.store.api.shared.exception.BusinessException;

public class EmployeeNotFoundException extends BusinessException {

    public EmployeeNotFoundException(String code) {
        super("Employee with code '%s' not found".formatted(code), EmployeeErrorCode.EMPLOYEE_NOT_FOUND);
    }

}
