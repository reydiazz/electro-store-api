package com.electro.store.api.domain.people.component;

import com.electro.store.api.domain.people.model.entity.Customer;
import com.electro.store.api.domain.people.web.response.CustomerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerMapper {

    private final PersonMapper personMapper;

    public CustomerResponse toResponse(Customer customer) {
        return new CustomerResponse(
                customer.getCode(),
                personMapper.toResponse(customer.getPerson()),
                customer.getTaxId()
        );
    }

}
