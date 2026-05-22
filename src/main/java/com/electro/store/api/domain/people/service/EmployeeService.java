package com.electro.store.api.domain.people.service;

import com.electro.store.api.domain.people.component.EmployeeMapper;
import com.electro.store.api.domain.people.exception.EmployeeNotFoundException;
import com.electro.store.api.domain.people.model.entity.Employee;
import com.electro.store.api.domain.people.model.entity.Person;
import com.electro.store.api.domain.people.repository.EmployeeRepository;
import com.electro.store.api.domain.people.web.request.CreateEmployeeRequest;
import com.electro.store.api.domain.people.web.request.UpdateEmployeeRequest;
import com.electro.store.api.domain.people.web.response.EmployeeResponse;
import com.electro.store.api.shared.utils.CodeGenerator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    public static final  String PREFIX = "EMP";
    public final EmployeeRepository employeeRepository;
    public final EmployeeMapper employeeMapper;

    private  final PersonService personService;

    @Transactional
    public EmployeeResponse create (CreateEmployeeRequest request){
        Person person = personService.create(request.person());
        String code = CodeGenerator.next(PREFIX);
        Employee employee = new Employee(
                code,
                person,
                request.position(),
                request.salary()
        );
        Employee saved = employeeRepository.save(employee);
        return employeeMapper.toResponse(saved);

    }
    @Transactional
    public EmployeeResponse update (String code, UpdateEmployeeRequest request){
        Employee employee = findByCodeOrThrow(code);
        Person person = personService.update(employee.getPerson().getCode(),request.person());
        employee.update(
                person,
                request.position(),
                request.salary()
        );
        return  employeeMapper.toResponse(employee);

    }
    @Transactional
    public void delete (String code){
        Employee employee =findByCodeOrThrow(code);
        employeeRepository.delete(employee);
    }

    public Employee findByCodeOrThrow(String code) {
        return employeeRepository.findById(code).orElseThrow(
                () -> new EmployeeNotFoundException(code)
        );
    }






}
