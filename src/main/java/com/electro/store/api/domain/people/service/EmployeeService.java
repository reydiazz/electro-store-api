package com.electro.store.api.domain.people.service;

import com.electro.store.api.domain.people.exception.employee.EmployeeNotFoundException;
import com.electro.store.api.domain.people.model.entity.Employee;
import com.electro.store.api.domain.people.model.entity.Person;
import com.electro.store.api.domain.people.repository.EmployeeRepository;
import com.electro.store.api.domain.people.web.request.CreateEmployeeRequest;
import com.electro.store.api.domain.people.web.request.UpdateEmployeeRequest;
import com.electro.store.api.shared.utils.CodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    public static final String PREFIX = "EMP";
    public final EmployeeRepository repository;

    private final PersonService personService;

    @Transactional(readOnly = true)
    public Page<Employee> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Transactional
    public Employee create(CreateEmployeeRequest request) {
        String code = CodeGenerator.next(PREFIX);
        Person person = personService.create(request.person());
        Employee employee = new Employee(code, person, request.position(), request.salary());
        return repository.save(employee);
    }

    @Transactional
    public Employee update(String code, UpdateEmployeeRequest request) {
        Employee employee = findByCodeOrThrow(code);
        Person person = personService.update(employee.getPerson().getCode(), request.person());
        employee.update(person, request.position(), request.salary());
        return employee;
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
