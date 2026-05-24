package com.electro.store.api.domain.people.service;

import com.electro.store.api.domain.people.exception.person.PersonNationalIdAlreadyExistsException;
import com.electro.store.api.domain.people.exception.person.PersonNotFoundException;
import com.electro.store.api.domain.people.model.entity.Person;
import com.electro.store.api.domain.people.repository.PersonRepository;
import com.electro.store.api.domain.people.web.request.CreatePersonRequest;
import com.electro.store.api.domain.people.web.request.UpdatePersonRequest;
import com.electro.store.api.shared.utils.CodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PersonService {

    public static final String PREFIX = "PEO";
    private final PersonRepository repository;

    @Transactional
    public Person create(CreatePersonRequest request) {
        ensureNationalIdIsUnique(request.nationalId());
        String code = CodeGenerator.next(PREFIX);
        Person person = new Person(
                code,
                request.firstName(),
                request.lastName(),
                request.phone(),
                request.nationalId()
        );
        return repository.save(person);
    }

    @Transactional
    public Person update(String code, UpdatePersonRequest request) {
        Person person = findByCodeOrThrow(code);
        ensureNationalIdIsUnique(request.nationalId(),code);
        person.update(
                request.firstName(),
                request.lastName(),
                request.phone(),
                request.nationalId()
        );
        return person;
    }

    @Transactional
    public void delete(String code) {
        Person person = findByCodeOrThrow(code);
        repository.delete(person);
    }

    public Person findByCodeOrThrow(String code) {
        return repository.findById(code).orElseThrow(
                () -> new PersonNotFoundException(code)
        );
    }

    private void ensureNationalIdIsUnique(String nationalId) {
        if (repository.existsByNationalId(nationalId)) {
            throw new PersonNationalIdAlreadyExistsException();
        }
    }

    private void ensureNationalIdIsUnique(String nationalId, String code) {
        if(repository.existsByNationalIdAndCodeNot(nationalId, code)) {
            throw new PersonNationalIdAlreadyExistsException();
        }
    }

}
