package com.electro.store.api.domain.sales.repository;

import com.electro.store.api.domain.sales.model.entity.Sale;
import com.electro.store.api.domain.sales.repository.projection.MonthlyRevenueProjection;
import com.electro.store.api.domain.sales.repository.projection.ProductQuantityProjection;
import com.electro.store.api.domain.sales.repository.projection.ProductRevenueProjection;
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

    //Query para los reportes:

    @Query("""
       SELECT new com.electro.store.api.domain.sales.repository.projection.MonthlyRevenueProjection(
           MONTH(s.saleDate),
           SUM(d.salePrice * d.quantity))
       FROM SaleDetail d
       JOIN d.sale s
       WHERE s.saleDate >= :startDate AND s.saleDate < :endDateExclusive
       GROUP BY MONTH(s.saleDate)
       ORDER BY MONTH(s.saleDate)
       """)
    List<MonthlyRevenueProjection> findMonthlyRevenue(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDateExclusive") LocalDateTime endDateExclusive
    );

    @Query("""
       SELECT new com.electro.store.api.domain.sales.repository.projection.ProductRevenueProjection(
           p.name,
           c.name,
           SUM(d.salePrice * d.quantity))
       FROM SaleDetail d
       JOIN d.product p
       JOIN p.category c
       JOIN d.sale s
       WHERE s.saleDate >= :startDate AND s.saleDate < :endDateExclusive
       GROUP BY p.code, p.name, c.name
       ORDER BY SUM(d.salePrice * d.quantity) DESC
       """)
    List<ProductRevenueProjection> findProductRevenueRanking(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDateExclusive") LocalDateTime endDateExclusive
    );

    @Query("""
       SELECT new com.electro.store.api.domain.sales.repository.projection.ProductQuantityProjection(
           p.name,
           c.name,
           SUM(d.quantity))
       FROM SaleDetail d
       JOIN d.product p
       JOIN p.category c
       JOIN d.sale s
       WHERE s.saleDate >= :startDate AND s.saleDate < :endDateExclusive
       GROUP BY p.code, p.name, c.name
       ORDER BY SUM(d.quantity) DESC
       """)
    List<ProductQuantityProjection> findProductQuantityRanking(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDateExclusive") LocalDateTime endDateExclusive
    );

}
