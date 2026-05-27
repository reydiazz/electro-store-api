package com.electro.store.api.domain.auth.web.request;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @NotBlank(message = "Username must not be blank")
        String username,
        @NotBlank(message = "Password must not be blank")
        String password
) {
}
