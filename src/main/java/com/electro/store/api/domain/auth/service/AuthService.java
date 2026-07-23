package com.electro.store.api.domain.auth.service;

import com.electro.store.api.domain.auth.component.AuthMapper;
import com.electro.store.api.domain.auth.exception.auth.AuthenticatedPrincipalNotFoundException;
import com.electro.store.api.domain.auth.exception.auth.NoAuthenticatedUserException;
import com.electro.store.api.domain.auth.model.entity.User;
import com.electro.store.api.domain.auth.web.request.LoginRequest;
import com.electro.store.api.domain.auth.web.response.LoginResponse;
import com.electro.store.api.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager manager;
    private final AuthMapper mapper;
    private final JwtService jwtService;

    public LoginResponse login(LoginRequest request) {
        Authentication authentication = manager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        String token = jwtService.generateToken(verifyPrincipal(principal));
        User user = principal.user();
        return new LoginResponse(token, mapper.toResponse(user));
    }

    private UserPrincipal verifyPrincipal(UserPrincipal principal) {
        if (principal == null) throw new AuthenticatedPrincipalNotFoundException();
        return principal;
    }

    public User getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            throw new NoAuthenticatedUserException();
        }
        Object principal = auth.getPrincipal();
        if (principal instanceof UserPrincipal(User user)) {
            return user;
        }
        throw new NoAuthenticatedUserException();
    }

}
