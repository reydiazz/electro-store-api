package com.electro.store.api.domain.auth.service;

import com.electro.store.api.domain.auth.exception.user.UserNotFoundException;
import com.electro.store.api.domain.auth.exception.user.UserSelfDeactivationException;
import com.electro.store.api.domain.auth.exception.user.UsernameAlreadyExistsException;
import com.electro.store.api.domain.auth.model.entity.User;
import com.electro.store.api.domain.auth.repository.UserRepository;
import com.electro.store.api.domain.auth.web.request.CreateUserRequest;
import com.electro.store.api.domain.auth.web.request.UpdateUserRequest;
import com.electro.store.api.domain.people.model.entity.Employee;
import com.electro.store.api.domain.people.service.EmployeeService;
import com.electro.store.api.shared.utils.CodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    public static final String PREFIX = "USR";
    private final UserRepository repository;

    private final AuthService authService;
    private final EmployeeService employeeService;

    @Transactional(readOnly = true)
    public Page<User> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Transactional
    public User create(CreateUserRequest request) {
        verifyUsername(request.username());
        Employee employee = employeeService.findByCodeOrThrow(request.employeeCode());
        String code = CodeGenerator.next(PREFIX);
        String password = new BCryptPasswordEncoder().encode(request.password());
        User user = new User(code, request.username(), password, request.role(), employee);
        return repository.save(user);
    }

    @Transactional
    public User update(UpdateUserRequest request, String code) {
        verifyUsername(request.username(), code);
        User user = findByCodeOrThrow(code);
        user.update(request.username(), request.password(), request.role());
        return user;
    }

    @Transactional
    public User deactivate(String code) {
        User user = findByCodeOrThrow(code);
        preventSelfDeactivation(user);
        user.deactivate();
        return user;
    }

    @Transactional
    public User activate(String code) {
        User user = findByCodeOrThrow(code);
        user.activate();
        return user;
    }

    @Transactional
    public void delete(String code) {
        User user = findByCodeOrThrow(code);
        repository.delete(user);
    }

    private void preventSelfDeactivation(User user) {
        User current = authService.getAuthenticatedUser();
        if (current.getCode().equals(user.getCode())) {
            throw new UserSelfDeactivationException();
        }
    }

    private void verifyUsername(String username, String code) {
        if (repository.existsByUsernameAndCodeNot(username, code)) {
            throw new UsernameAlreadyExistsException(username);
        }
    }

    private void verifyUsername(String username) {
        if (repository.existsByUsername(username)) {
            throw new UsernameAlreadyExistsException(username);
        }
    }

    public User findByCodeOrThrow(String code) {
        return repository.findById(code).orElseThrow(
                () -> new UserNotFoundException(code)
        );
    }

}
