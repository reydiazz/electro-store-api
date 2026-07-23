package com.electro.store.api.domain.people.repository;

import com.electro.store.api.domain.people.model.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, String> {

    Optional<Employee> findByPersonNationalId(String nationalId);

}
