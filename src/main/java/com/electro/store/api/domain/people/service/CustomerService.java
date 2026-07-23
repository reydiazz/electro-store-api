package com.electro.store.api.domain.people.service;

import com.electro.store.api.domain.people.exception.customer.CustomerNotFoundException;
import com.electro.store.api.domain.people.exception.customer.CustomerTaxIdAlreadyExistsException;
import com.electro.store.api.domain.people.model.entity.Customer;
import com.electro.store.api.domain.people.model.entity.Person;
import com.electro.store.api.domain.people.repository.CustomerRepository;
import com.electro.store.api.domain.people.web.request.CreateCustomerRequest;
import com.electro.store.api.domain.people.web.request.UpdateCustomerRequest;
import com.electro.store.api.shared.utils.CodeGenerator;
import com.electro.store.api.domain.people.web.response.CustomerMetricsResponse;
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

    public final PersonService personService;

    @Transactional(readOnly = true)
    public CustomerMetricsResponse getMetrics() {
        long total = repository.count();
        long withDni = repository.countWithDni();
        long withRuc = repository.countWithRuc();
        return new CustomerMetricsResponse(total, withDni, withRuc);
    }

    @Transactional(readOnly = true)
    public Page<Customer> findAll(String search, Pageable pageable) {
        if (search != null && !search.trim().isEmpty()) {
            return repository.search(search.trim(), pageable);
        }
        return repository.findAll(pageable);
    }

    @Transactional
    public Customer create(CreateCustomerRequest request) {
        verifyTaxId(request.taxId());
        String code = CodeGenerator.next(PREFIX);
        Person person = personService.create(request.person());
        Customer customer = new Customer(code, person, request.taxId());
        return repository.save(customer);
    }

    @Transactional
    public Customer update(String code, UpdateCustomerRequest request) {
        verifyTaxId(request.taxId(), code);
        Customer customer = findByCodeOrThrow(code);
        Person person = personService.update(customer.getPerson().getCode(), request.person());
        customer.update(person, request.taxId());
        return customer;
    }

    @Transactional
    public void delete(String code) {
        Customer customer = findByCodeOrThrow(code);
        repository.delete(customer);
        personService.delete(customer.getPerson().getCode());
    }

    public Customer findByCodeOrThrow(String code) {
        return repository.findById(code).orElseThrow(
                () -> new CustomerNotFoundException(code)
        );
    }

    private void verifyTaxId(String taxId, String code) {
        if (taxId != null && repository.existsByTaxIdAndCodeNot(taxId, code)) {
            throw new CustomerTaxIdAlreadyExistsException(taxId);
        }
    }

    private void verifyTaxId(String taxId) {
        if (taxId != null && repository.existsByTaxId(taxId)) {
            throw new CustomerTaxIdAlreadyExistsException(taxId);
        }
    }

}

