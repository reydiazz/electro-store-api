package com.electro.store.api.domain.people.web.request;

import com.electro.store.api.domain.people.model.enums.EmployeePosition;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record UpdateEmployeeRequest(
        @NotNull(message = "Employee data is required")
        UpdatePersonRequest person,
        @NotNull(message = "Employee position is required")
        EmployeePosition position,
        @NotNull(message = "Employee salary not be empty")
        @DecimalMin(value = "0.01", message = "Employee salary  must be greater than zero")
        BigDecimal salary
) {
}
