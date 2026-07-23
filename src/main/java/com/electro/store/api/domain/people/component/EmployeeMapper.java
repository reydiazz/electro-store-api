package com.electro.store.api.domain.people.component;

import com.electro.store.api.domain.people.model.entity.Employee;
import com.electro.store.api.domain.people.web.response.EmployeeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmployeeMapper {

    private final PersonMapper personMapper;

    public EmployeeResponse toResponse(Employee employee) {
        return new EmployeeResponse(
                employee.getCode(),
                personMapper.toResponse(employee.getPerson()),
                employee.getPosition(),
                employee.getSalary()
        );
    }

}
