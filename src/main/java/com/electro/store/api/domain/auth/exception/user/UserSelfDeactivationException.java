package com.electro.store.api.domain.auth.exception.user;

import com.electro.store.api.shared.exception.BusinessException;

public class UserSelfDeactivationException extends BusinessException {

    public UserSelfDeactivationException() {
        super("A user is not allowed to deactivate their own account.", UserErrorCode.USER_SELF_DEACTIVATION);
    }

}
