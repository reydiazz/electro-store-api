package com.electro.store.api.domain.people.repository;

import com.electro.store.api.domain.people.model.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, String> {
}
