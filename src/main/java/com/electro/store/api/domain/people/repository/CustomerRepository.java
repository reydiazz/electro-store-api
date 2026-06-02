package com.electro.store.api.domain.people.repository;

import com.electro.store.api.domain.people.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, String> {

    boolean existsByTaxId(String taxId);

    boolean existsByTaxIdAndCodeNot(String taxId, String code);

}
