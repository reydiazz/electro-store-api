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

    @Query("""
       SELECT CAST(s.saleDate AS date), COALESCE(SUM(d.salePrice * d.quantity), 0)
       FROM Sale s JOIN s.details d
       WHERE s.saleDate >= :startDate
       GROUP BY CAST(s.saleDate AS date)
       ORDER BY CAST(s.saleDate AS date)
       """)
    List<Object[]> findDailySalesTotals(@Param("startDate") LocalDateTime startDate);

    @Query("""
       SELECT d.product.name, COALESCE(SUM(d.quantity), 0)
       FROM SaleDetail d
       WHERE d.sale.saleDate >= :startDate
       GROUP BY d.product.name
       ORDER BY COALESCE(SUM(d.quantity), 0) DESC
       """)
    List<Object[]> findTopSellingProducts(@Param("startDate") LocalDateTime startDate, Pageable pageable);

    @Query("""
       SELECT new com.electro.store.api.domain.sales.web.response.DashboardProjection(
           COALESCE(SUM(d.salePrice * d.quantity), 0),
           COUNT(DISTINCT s)
       )
       FROM Sale s LEFT JOIN s.details d
       WHERE s.saleDate BETWEEN :startDate AND :endDate
       """)
    com.electro.store.api.domain.sales.web.response.DashboardProjection getDashboardTotals(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
}
