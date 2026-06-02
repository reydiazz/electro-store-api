package com.electro.store.api.domain.auth.exception.user;

import com.electro.store.api.shared.exception.BusinessException;

public class UserNotFoundException extends BusinessException {

    public UserNotFoundException(String code) {
        super("User with code '%s' not found".formatted(code), UserErrorCode.USER_NOT_FOUND);
    }

}
