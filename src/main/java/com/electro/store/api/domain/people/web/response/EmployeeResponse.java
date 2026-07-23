package com.electro.store.api.domain.people.web.response;

import com.electro.store.api.domain.people.model.enums.EmployeePosition;

import java.math.BigDecimal;

public record EmployeeResponse(
        String code,
        PersonResponse person,
        EmployeePosition position,
        BigDecimal salary
) {
}
