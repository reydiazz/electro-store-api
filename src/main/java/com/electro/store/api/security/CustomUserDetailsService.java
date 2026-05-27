package com.electro.store.api.security;

import com.electro.store.api.domain.auth.model.entity.User;
import com.electro.store.api.domain.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repository;
    private static final String USER_NOT_FOUND = "User not found with username: ";

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = repository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(USER_NOT_FOUND + username)
        );
        return new UserPrincipal(user);
    }

}
