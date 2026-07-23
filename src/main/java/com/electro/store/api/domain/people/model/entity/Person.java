package com.electro.store.api.domain.people.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "people")
@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class Person {

    @Id
    @Column(name = "code", unique = true, nullable = false)
    private String code;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "national_id", unique = true)
    private String nationalId;

    @Column(name = "phone", unique = true)
    private String phone;

    public Person(String code, String firstName, String lastName, String phone, String nationalId) {
        this.code = code;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.nationalId = nationalId;
    }

    public void update(String firstName, String lastName, String phone, String nationalId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.nationalId = nationalId;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

}
