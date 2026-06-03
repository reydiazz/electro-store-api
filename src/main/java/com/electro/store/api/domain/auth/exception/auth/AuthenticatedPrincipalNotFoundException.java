package com.electro.store.api.domain.auth.exception.auth;

import com.electro.store.api.shared.exception.BusinessException;

public class AuthenticatedPrincipalNotFoundException extends BusinessException {

    public AuthenticatedPrincipalNotFoundException() {
        super("Authenticated principal not found", AuthErrorCode.AUTHENTICATED_PRINCIPAL_NOT_FOUND);
    }

}
