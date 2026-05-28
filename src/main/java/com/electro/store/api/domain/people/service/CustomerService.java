package com.electro.store.api.domain.people.service;

import com.electro.store.api.domain.people.component.CustomerMapper;
import com.electro.store.api.domain.people.exception.employee.EmployeeNotFoundException;
import com.electro.store.api.domain.people.model.entity.Customer;
import com.electro.store.api.domain.people.model.entity.Person;
import com.electro.store.api.domain.people.repository.CustomerRepository;
import com.electro.store.api.domain.people.web.request.CreateCustomerRequest;
import com.electro.store.api.domain.people.web.request.UpdateCustomerRequest;
import com.electro.store.api.domain.people.web.response.CustomerResponse;
import com.electro.store.api.shared.utils.CodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    public static final String PREFIX = "CUS";
    public final CustomerRepository repository;
    public final CustomerMapper mapper;

    public final PersonService personService;

    @Transactional(readOnly = true)
    public Page<CustomerResponse> findAll(Pageable pageable) {
        Page<Customer> customers = repository.findAll(pageable);
        return customers.map(mapper::toResponse);
    }

    @Transactional
    public CustomerResponse create(CreateCustomerRequest request) {
        String code = CodeGenerator.next(PREFIX);
        Person person = personService.create(request.person());
        Customer customer = new Customer(code, person, request.taxId());
        Customer saved = repository.save(customer);
        return mapper.toResponse(saved);
    }

    @Transactional
    public CustomerResponse update(String code, UpdateCustomerRequest request) {
        Customer customer = findByCodeOrThrow(code);
        Person person = personService.update(customer.getPerson().getCode(), request.person());
        customer.update(person, request.taxId());
        return mapper.toResponse(customer);
    }

    @Transactional
    public void delete(String code) {
        Customer customer = findByCodeOrThrow(code);
        repository.delete(customer);
        personService.delete(customer.getPerson().getCode());
    }

    public Customer findByCodeOrThrow(String code) {
        return repository.findById(code).orElseThrow(
                () -> new EmployeeNotFoundException(code)
        );
    }

}

