package com.electro.store.api.domain.auth.web.controller;

import com.electro.store.api.domain.auth.component.UserMapper;
import com.electro.store.api.domain.auth.model.entity.User;
import com.electro.store.api.domain.auth.service.UserService;
import com.electro.store.api.domain.auth.web.request.CreateUserRequest;
import com.electro.store.api.domain.auth.web.response.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/users")
@PreAuthorize("hasAnyRole('ADMIN')")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final UserMapper mapper;

    @GetMapping
    public ResponseEntity<Page<UserResponse>> findAll(Pageable pageable) {
        Page<User> page = service.findAll(pageable);
        return ResponseEntity.ok(page.map(mapper::toResponse));
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@Valid @RequestBody CreateUserRequest request) {
        User user = service.create(request);
        return ResponseEntity.ok(mapper.toResponse(user));
    }

}
