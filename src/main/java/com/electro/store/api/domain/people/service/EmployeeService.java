package com.electro.store.api.domain.people.service;

import com.electro.store.api.domain.people.component.EmployeeMapper;
import com.electro.store.api.domain.people.exception.employee.EmployeeNotFoundException;
import com.electro.store.api.domain.people.model.entity.Employee;
import com.electro.store.api.domain.people.model.entity.Person;
import com.electro.store.api.domain.people.repository.EmployeeRepository;
import com.electro.store.api.domain.people.web.request.CreateEmployeeRequest;
import com.electro.store.api.domain.people.web.request.UpdateEmployeeRequest;
import com.electro.store.api.domain.people.web.response.EmployeeResponse;
import com.electro.store.api.shared.utils.CodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    public static final String PREFIX = "EMP";
    public final EmployeeRepository repository;
    public final EmployeeMapper mapper;

    private final PersonService personService;

    @Transactional(readOnly = true)
    public Page<EmployeeResponse> findAll(Pageable pageable) {
        Page<Employee> employees = repository.findAll(pageable);
        return employees.map(mapper::toResponse);
    }

    @Transactional
    public EmployeeResponse create(CreateEmployeeRequest request) {
        String code = CodeGenerator.next(PREFIX);
        Person person = personService.create(request.person());
        Employee employee = new Employee(code, person, request.position(), request.salary());
        Employee saved = repository.save(employee);
        return mapper.toResponse(saved);
    }

    @Transactional
    public EmployeeResponse update(String code, UpdateEmployeeRequest request) {
        Employee employee = findByCodeOrThrow(code);
        Person person = personService.update(employee.getPerson().getCode(), request.person());
        employee.update(person, request.position(), request.salary());
        return mapper.toResponse(employee);
    }

    @Transactional
    public void delete(String code) {
        Employee employee = findByCodeOrThrow(code);
        repository.delete(employee);
    }

    public Employee findByCodeOrThrow(String code) {
        return repository.findById(code).orElseThrow(
                () -> new EmployeeNotFoundException(code)
        );
    }

}
