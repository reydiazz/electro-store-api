package com.electro.store.api.domain.auth.exception.user;

import com.electro.store.api.shared.exception.BusinessException;

public class UsernameAlreadyExistsException extends BusinessException {

    public UsernameAlreadyExistsException(String username) {
        super("User with username '%s' already exists".formatted(username), UserErrorCode.USERNAME_ALREADY_EXISTS);
    }

}
