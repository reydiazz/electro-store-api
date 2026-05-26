package com.electro.store.api.domain.people.service;

import com.electro.store.api.domain.people.exception.PersonNotFoundException;
import com.electro.store.api.domain.people.model.entity.Person;
import com.electro.store.api.domain.people.repository.PersonRepository;
import com.electro.store.api.domain.people.web.request.CreatePersonRequest;
import com.electro.store.api.domain.people.web.request.UpdatePersonRequest;
import com.electro.store.api.shared.utils.CodeGenerator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonService {
    public  static final String PREFIX="PEO";
    private final PersonRepository repository;

    @Transactional
    public Person create (CreatePersonRequest request){
        String code = CodeGenerator.next(PREFIX);
        Person person = new Person(
                code,
                request.firstName(),
                request.lastName(),
                request.phone(),
                request.nationalId()
        );
        return  repository.save(person);
    }

    @Transactional
    public Person update (String code ,UpdatePersonRequest request){
        Person person = findByCodeOrThrow(code);
        person.update(
                request.firstName(),
                request.lastName(),
                request.phone(),
                request.nationalId()
        );
        return person;
    }

    @Transactional
    public void delete (String code){
        Person person = findByCodeOrThrow(code);
        repository.delete(person);
    }

    public Person findByCodeOrThrow(String code) {
        return repository.findById(code).orElseThrow(
                () -> new PersonNotFoundException(code)
        );
    }



}
