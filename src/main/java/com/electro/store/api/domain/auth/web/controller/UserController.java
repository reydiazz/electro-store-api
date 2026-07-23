package com.electro.store.api.domain.auth.web.controller;

import com.electro.store.api.domain.auth.component.UserMapper;
import com.electro.store.api.domain.auth.model.entity.User;
import com.electro.store.api.domain.auth.model.enums.Role;
import com.electro.store.api.domain.auth.service.UserService;
import com.electro.store.api.domain.auth.web.request.CreateUserRequest;
import com.electro.store.api.domain.auth.web.request.UpdateUserRequest;
import com.electro.store.api.domain.auth.web.response.UserResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
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

    @GetMapping("/roles")
    public ResponseEntity<Role[]> getRoles() {
        return ResponseEntity.ok(Role.values());
    }

    @PostMapping
    public ResponseEntity<UserResponse> create(@Valid @RequestBody CreateUserRequest request) {
        User user = service.create(request);
        return ResponseEntity.ok(mapper.toResponse(user));
    }

    @PutMapping("/{code}")
    public ResponseEntity<UserResponse> update(@Valid @RequestBody UpdateUserRequest request, @PathVariable String code) {
        User user = service.update(request, code);
        return ResponseEntity.ok(mapper.toResponse(user));
    }

    @PatchMapping("/deactivate/{code}")
    public ResponseEntity<UserResponse> deactivate(@PathVariable String code) {
        User user = service.deactivate(code);
        return ResponseEntity.ok(mapper.toResponse(user));
    }

    @PatchMapping("/activate/{code}")
    public ResponseEntity<UserResponse> activate(@PathVariable String code) {
        User user = service.activate(code);
        return ResponseEntity.ok(mapper.toResponse(user));
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<Void> delete(@PathVariable String code) {
        service.delete(code);
        return ResponseEntity.noContent().build();
    }

}
