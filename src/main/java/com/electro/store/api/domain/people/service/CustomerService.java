package com.electro.store.api.domain.people.service;

import com.electro.store.api.domain.people.exception.person.PersonNotFoundException;
import com.electro.store.api.domain.people.model.entity.Customer;
import com.electro.store.api.domain.people.model.entity.Person;
import com.electro.store.api.domain.people.repository.CustomerRepository;
import com.electro.store.api.domain.people.web.request.CreateCustomerRequest;
import com.electro.store.api.domain.people.web.request.UpdateCustomerRequest;
import com.electro.store.api.shared.utils.CodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    public static final String PREFIX = "CUS";
    private final CustomerRepository repository;
    private final PersonService personService;

    @Transactional
    public Customer create(CreateCustomerRequest request) {
        String code = CodeGenerator.next(PREFIX);
        Person person = personService.create(
                request.person()
        );
        Customer customer = new Customer(
                code,
                person,
                request.taxId()
        );
        return repository.save(customer);
    }

    @Transactional
    public Customer update(String code, UpdateCustomerRequest request) {
        Customer customer = findByCodeOrThrow(code);
        Person person = personService.update(
                customer.getPerson().getCode(),
                request.person()
        );
        customer.update(
                person,
                request.taxId()
        );
        return customer;
    }

    @Transactional
    public void delete(String code) {
        Customer customer = findByCodeOrThrow(code);
        personService.delete(customer.getPerson().getCode());
        repository.delete(customer);
    }

    public Customer findByCodeOrThrow(String code) {
        return repository.findById(code).orElseThrow(
                () -> new PersonNotFoundException(code)
        );
    }

}
