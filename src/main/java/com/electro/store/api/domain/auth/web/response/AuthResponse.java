package com.electro.store.api.domain.auth.web.response;

import com.electro.store.api.domain.auth.model.enums.Role;

public record AuthResponse(
        String code,
        String username,
        String firstName,
        String lastName,
        Role role
) {
}

