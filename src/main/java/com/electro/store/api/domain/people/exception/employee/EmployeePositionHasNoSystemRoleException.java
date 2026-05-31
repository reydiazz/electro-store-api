package com.electro.store.api.domain.people.exception.employee;

import com.electro.store.api.domain.people.model.enums.EmployeePosition;
import com.electro.store.api.shared.exception.BusinessException;

public class EmployeePositionHasNoSystemRoleException extends BusinessException {

    public EmployeePositionHasNoSystemRoleException(EmployeePosition position) {
        super("Position '%s' has no role assigned in the system".formatted(position.name()), EmployeeErrorCode.EMPLOYEE_POSITION_HAS_NO_SYSTEM_ROLE);
    }

}
