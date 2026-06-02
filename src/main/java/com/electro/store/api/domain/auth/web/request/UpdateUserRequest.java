package com.electro.store.api.domain.auth.web.request;

import com.electro.store.api.domain.auth.model.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateUserRequest(
        @NotBlank(message = "Username is required")
        String username,
        @NotBlank(message = "Password is required")
        String password,
        @NotNull
        Role role
) {
}
