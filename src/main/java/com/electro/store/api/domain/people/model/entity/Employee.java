package com.electro.store.api.domain.people.model.entity;

import com.electro.store.api.domain.auth.model.enums.Role;
import com.electro.store.api.domain.people.exception.employee.EmployeePositionHasNoSystemRoleException;
import com.electro.store.api.domain.people.model.enums.EmployeePosition;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "employees")
@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class Employee {

    @Id
    @Column(name = "code", unique = true, nullable = false)
    private String code;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_code", unique = true, nullable = false)
    private Person person;

    @Enumerated(EnumType.STRING)
    @Column(name = "position", nullable = false)
    private EmployeePosition position;

    @Column(name = "salary", nullable = false)
    private BigDecimal salary;

    public Employee(String code, Person person, EmployeePosition position, BigDecimal salary) {
        this.code = code;
        this.person = person;
        this.position = position;
        this.salary = salary;
    }

    public void update(Person person, EmployeePosition position, BigDecimal salary) {
        this.person = person;
        this.position = position;
        this.salary = salary;
    }

    public Role getSystemRole() {
        return switch (this.position) {
            case MANAGER -> Role.ADMIN;
            case SELLER -> Role.RECEPTION;
            case STOREKEEPER -> Role.STOREKEEPER;
            default -> throw new EmployeePositionHasNoSystemRoleException(this.position);
        };
    }

}
