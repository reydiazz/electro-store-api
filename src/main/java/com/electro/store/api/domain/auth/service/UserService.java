package com.electro.store.api.domain.auth.service;

import com.electro.store.api.domain.auth.exception.user.UserNotFoundException;
import com.electro.store.api.domain.auth.model.entity.User;
import com.electro.store.api.domain.auth.repository.UserRepository;
import com.electro.store.api.domain.auth.web.request.CreateUserRequest;
import com.electro.store.api.domain.people.model.entity.Employee;
import com.electro.store.api.domain.people.service.EmployeeService;
import com.electro.store.api.shared.utils.CodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    public static final String PREFIX = "USR";
    private final UserRepository repository;

    private final EmployeeService employeeService;

    @Transactional(readOnly = true)
    public Page<User> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Transactional
    public User create(CreateUserRequest request) {
        Employee employee = employeeService.findByCodeOrThrow(request.employeeCode());
        String code = CodeGenerator.next(PREFIX);
        User user = new User(
                code,
                request.username(),
                request.password(),
                employee
        );
        return repository.save(user);
    }

    public User findByCodeOrThrow(String code){
        return repository.findById(code).orElseThrow(
                () -> new UserNotFoundException(code)
        );

    }

}
