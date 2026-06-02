package com.electro.store.api.domain.people.repository;

import com.electro.store.api.domain.people.model.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, String> {

    boolean existsByNationalId(String nationalId);

    boolean existsByNationalIdAndCodeNot(String nationalId, String code);

    boolean existsByPhoneAndCodeNot(String phone, String code);

    boolean existsByPhone(String phone);

}
