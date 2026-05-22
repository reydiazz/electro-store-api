package com.electro.store.api.domain.people.service;


import com.electro.store.api.domain.people.component.CustomerMapper;
import com.electro.store.api.domain.people.exception.EmployeeNotFoundException;
import com.electro.store.api.domain.people.model.entity.Customer;
import com.electro.store.api.domain.people.model.entity.Employee;
import com.electro.store.api.domain.people.model.entity.Person;
import com.electro.store.api.domain.people.repository.CustomerRepository;
import com.electro.store.api.domain.people.web.request.CreateCustomerRequest;
import com.electro.store.api.domain.people.web.request.UpdateCustomerRequest;
import com.electro.store.api.domain.people.web.response.CustomerResponse;
import com.electro.store.api.shared.utils.CodeGenerator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    public static  final  String PREFIX="CUS";
    public  final CustomerRepository customerRepository;
    public final CustomerMapper customerMapper;

    public  final PersonService personService;

    @Transactional
    public CustomerResponse create (CreateCustomerRequest request){
        String code = CodeGenerator.next(PREFIX);
        Person person = personService.create(request.person());
        Customer customer = new Customer(
                code,
                person,
                request.taxId()
        );
        Customer saved = customerRepository.save(customer);
        return  customerMapper.toResponse(saved);
    }

    @Transactional
    public  CustomerResponse update (String code,UpdateCustomerRequest request){
        Customer customer = findByCodeOrThrow(code);
        Person person = personService.update(customer.getPerson().getCode(),request.person());
        customer.update(
                person,
                request.taxId()
        );
        return customerMapper.toResponse(customer);
    }
    @Transactional

    public void delete (String code){
        Customer customer = findByCodeOrThrow(code);
        customerRepository.delete(customer);
        personService.delete(customer.getPerson().getCode());
    }


    public Customer findByCodeOrThrow(String code) {
        return customerRepository.findById(code).orElseThrow(
                () -> new EmployeeNotFoundException(code)
        );
    }

}

