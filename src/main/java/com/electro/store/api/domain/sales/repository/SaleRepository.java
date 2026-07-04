package com.electro.store.api.domain.sales.repository;

import com.electro.store.api.domain.sales.model.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, String> {
    @Query("SELECT s FROM Sale s WHERE " +
           "LOWER(s.code) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(s.customer.person.firstName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(s.customer.person.lastName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(s.customer.person.nationalId) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(s.customer.taxId) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(s.user.username) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(s.user.employee.person.firstName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(s.user.employee.person.lastName) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<Sale> search(@Param("search") String search, Pageable pageable);

    @Query("""
       SELECT s
       FROM Sale s
       WHERE s.saleDate BETWEEN :startDate AND :endDate
       """)
    List<Sale> findBySaleDateBetween(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

}
