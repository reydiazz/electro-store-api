package com.electro.store.api.domain.auth.web.response;

import com.electro.store.api.domain.auth.model.enums.Role;

public record UserResponse(
        String code,
        String username,
        String employeeCode,
        String firstName,
        String lastName,
        Role role
) {
}
