package com.electro.store.api.domain.auth.model.entity;

import com.electro.store.api.domain.auth.model.enums.Role;
import com.electro.store.api.domain.people.model.entity.Employee;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "users")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User {

    @Id
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @OneToOne
    @JoinColumn(name = "employee_code", unique = true)
    private Employee employee;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    public User(String code, String username, String password, Role role,Employee employee) {
        this.code = code;
        this.username = username;
        this.password = password;
        this.role = role;
        this.employee = employee;
    }

    public void update(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

}
