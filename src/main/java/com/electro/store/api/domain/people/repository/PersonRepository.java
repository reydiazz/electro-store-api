package com.electro.store.api.domain.people.repository;

import com.electro.store.api.domain.people.model.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, String> {
}
