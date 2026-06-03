package com.electro.store.api.domain.auth.component;

import com.electro.store.api.domain.auth.model.entity.User;
import com.electro.store.api.domain.auth.web.response.UserResponse;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse toResponse(User user) {
        return new UserResponse(
                user.getCode(),
                user.getUsername(),
                user.getEmployee().getCode(),
                user.getEmployee().getPerson().getFirstName(),
                user.getEmployee().getPerson().getLastName(),
                user.getRole()
        );
    }

}
