package com.electro.store.api.domain.buys.repository;

import com.electro.store.api.domain.buys.model.entity.Purchases;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PurchasesRepository extends JpaRepository<Purchases, String> {

    @Query("SELECT DISTINCT p FROM Purchases p " +
            "JOIN FETCH p.supplier " +
            "JOIN FETCH p.user u " +
            "JOIN FETCH u.employee e " +
            "JOIN FETCH e.person " +
            "WHERE (:search IS NULL OR " +
            "LOWER(p.code) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(p.supplier.tradeName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(p.supplier.legalName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(p.supplier.taxId) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(p.user.username) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(p.user.employee.person.firstName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(p.user.employee.person.lastName) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<Purchases> search(@Param("search") String search, Pageable pageable);

    @Query("SELECT COUNT(p) FROM Purchases p WHERE p.purchaseDate >= :startDate")
    long countWeeklyPurchases(@Param("startDate") LocalDateTime startDate);

    @Query("SELECT COALESCE(SUM(d.quantity), 0) FROM PurchasesDetails d WHERE d.purchase.purchaseDate >= :startDate")
    long countMonthlyProductsEntered(@Param("startDate") LocalDateTime startDate);

    @Query("SELECT p.supplier.tradeName, COUNT(p) " +
            "FROM Purchases p " +
            "GROUP BY p.supplier.tradeName " +
            "ORDER BY COUNT(p) DESC")
    List<Object[]> findFrequentSupplier(Pageable pageable);

    List<Purchases> findByPurchaseDateBetween(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );

    @Query("""
       SELECT CAST(p.purchaseDate AS date), COALESCE(SUM(d.purchasePrice * d.quantity), 0)
       FROM Purchases p JOIN p.details d
       WHERE p.purchaseDate >= :startDate
       GROUP BY CAST(p.purchaseDate AS date)
       ORDER BY CAST(p.purchaseDate AS date)
       """)
    List<Object[]> findDailyPurchasesTotals(@Param("startDate") LocalDateTime startDate);

    @Query("""
       SELECT new com.electro.store.api.domain.buys.web.response.PurchaseDashboardProjection(
           COALESCE(SUM(d.purchasePrice * d.quantity), 0),
           COUNT(DISTINCT p)
       )
       FROM Purchases p LEFT JOIN p.details d
       WHERE p.purchaseDate BETWEEN :startDate AND :endDate
       """)
    com.electro.store.api.domain.buys.web.response.PurchaseDashboardProjection getDashboardTotals(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate
    );
}