package com.electro.store.api.domain.people.repository;

import com.electro.store.api.domain.people.model.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends JpaRepository<Customer, String> {

    boolean existsByTaxId(String taxId);

    boolean existsByTaxIdAndCodeNot(String taxId, String code);

    @Query("SELECT c FROM Customer c WHERE " +
            "LOWER(c.code) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(c.taxId) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(c.person.firstName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(c.person.lastName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(c.person.nationalId) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<Customer> search(@Param("search") String search, Pageable pageable);

    @Query("SELECT COUNT(c) FROM Customer c WHERE c.person.nationalId IS NOT NULL AND c.person.nationalId <> ''")
    long countWithDni();

    @Query("SELECT COUNT(c) FROM Customer c WHERE c.taxId IS NOT NULL AND c.taxId <> ''")
    long countWithRuc();

}

