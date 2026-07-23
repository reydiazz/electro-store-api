package com.electro.store.api.domain.auth.web.response;

public record LoginResponse(
        String token,
        AuthResponse auth
) {
}
