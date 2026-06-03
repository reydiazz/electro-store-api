package com.electro.store.api.domain.auth.exception.auth;

import com.electro.store.api.shared.exception.BusinessException;

public class NoAuthenticatedUserException extends BusinessException {

    public NoAuthenticatedUserException() {
        super("No authenticated user found in security context", AuthErrorCode.NO_AUTHENTICATED_USER);
    }

}
