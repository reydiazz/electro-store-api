package com.electro.store.api.domain.people.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "customers")
@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer {

    @Id
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_code", unique = true, nullable = false)
    private Person person;

    @Column(name = "tax_id", unique = true)
    private String taxId;

    public Customer(String code, Person person, String taxId) {
        this.code = code;
        this.person = person;
        this.taxId = taxId;
    }

    public void update(Person person, String taxId) {
        this.person = person;
        this.taxId = taxId;
    }

}
