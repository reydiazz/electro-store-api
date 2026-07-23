package com.electro.store.api.domain.people.component;

import com.electro.store.api.domain.people.model.entity.Person;
import com.electro.store.api.domain.people.web.response.PersonResponse;
import org.springframework.stereotype.Component;

@Component
public class PersonMapper {

    public PersonResponse toResponse(Person person) {
        return new PersonResponse(
                person.getFirstName(),
                person.getLastName(),
                person.getNationalId(),
                person.getPhone()
        );
    }

}
