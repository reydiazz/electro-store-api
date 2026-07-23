package com.electro.store.api.domain.auth.web.request;

import com.electro.store.api.domain.auth.model.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateUserRequest(
        @NotBlank(message = "Username is required")
        String username,
        @NotBlank(message = "Password is required")
        String password,
        @NotNull
        Role role,
        @NotBlank(message = "Employee code is required")
        String employeeCode
) {
}
