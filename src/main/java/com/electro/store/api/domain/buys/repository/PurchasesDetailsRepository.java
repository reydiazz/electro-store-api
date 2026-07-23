package com.electro.store.api.domain.buys.repository;

import com.electro.store.api.domain.buys.model.entity.PurchasesDetails;
import com.electro.store.api.domain.buys.repository.projection.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PurchasesDetailsRepository  extends JpaRepository<PurchasesDetails,String> {
    @Query("""
        SELECT COALESCE(SUM(d.quantity), 0)
        FROM PurchasesDetails d
        WHERE d.product.code = :productCode
          AND d.purchase.purchaseDate < :startDate
        """)
    Integer getPurchasedQuantityBefore(
            @Param("productCode") String productCode,
            @Param("startDate") LocalDateTime startDate
    );

    @Query("""
        SELECT new com.electro.store.api.domain.buys.repository.projection.PurchaseMovementProjection(
            d.product.code, p.purchaseDate, p.code, s.tradeName, d.quantity, d.purchasePrice)
        FROM PurchasesDetails d
        JOIN d.purchase p
        JOIN p.supplier s
        WHERE p.purchaseDate <= :endDate
          AND (:productCode IS NULL OR d.product.code = :productCode)
        ORDER BY d.product.code, p.purchaseDate
        """)
    List<PurchaseMovementProjection> findPurchaseMovementsUntil(
            @Param("productCode") String productCode,
            @Param("endDate") LocalDateTime endDate
    );

    @Query("""
        SELECT new com.electro.store.api.domain.buys.repository.projection.PurchaseTotalProjection(
            p.purchaseDate, SUM(d.purchasePrice * d.quantity))
        FROM PurchasesDetails d
        JOIN d.purchase p
        WHERE p.purchaseDate >= :startDate AND p.purchaseDate < :endDateExclusive
        GROUP BY p.code, p.purchaseDate
        ORDER BY p.purchaseDate
        """)
    List<PurchaseTotalProjection> findPurchaseTotals(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDateExclusive") LocalDateTime endDateExclusive
    );

    @Query("""
        SELECT new com.electro.store.api.domain.buys.repository.projection.PurchaseKpiProjection(
            COUNT(DISTINCT p.code), SUM(d.quantity), SUM(d.purchasePrice * d.quantity))
        FROM PurchasesDetails d
        JOIN d.purchase p
        WHERE p.purchaseDate >= :startDate AND p.purchaseDate < :endDateExclusive
        """)
    PurchaseKpiProjection findPurchaseKpis(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDateExclusive") LocalDateTime endDateExclusive
    );

    @Query("""
        SELECT new com.electro.store.api.domain.buys.repository.projection.SupplierPurchaseProjection(
            s.tradeName, COUNT(DISTINCT p.code), SUM(d.purchasePrice * d.quantity))
        FROM PurchasesDetails d
        JOIN d.purchase p
        JOIN p.supplier s
        WHERE p.purchaseDate >= :startDate AND p.purchaseDate < :endDateExclusive
        GROUP BY s.code, s.tradeName
        ORDER BY SUM(d.purchasePrice * d.quantity) DESC
        """)
    List<SupplierPurchaseProjection> findSupplierRanking(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDateExclusive") LocalDateTime endDateExclusive
    );

    @Query("""
        SELECT new com.electro.store.api.domain.buys.repository.projection.ProductPurchaseProjection(
            pr.name, SUM(d.quantity), SUM(d.purchasePrice * d.quantity))
        FROM PurchasesDetails d
        JOIN d.purchase p
        JOIN d.product pr
        WHERE p.purchaseDate >= :startDate AND p.purchaseDate < :endDateExclusive
        GROUP BY pr.code, pr.name
        ORDER BY SUM(d.purchasePrice * d.quantity) DESC
        """)
    List<ProductPurchaseProjection> findProductPurchaseRanking(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDateExclusive") LocalDateTime endDateExclusive
    );

    @Query("""
        SELECT new com.electro.store.api.domain.buys.repository.projection.CategorySpendingProjection(
            c.name, SUM(d.purchasePrice * d.quantity))
        FROM PurchasesDetails d
        JOIN d.purchase p
        JOIN d.product pr
        JOIN pr.category c
        WHERE p.purchaseDate >= :startDate AND p.purchaseDate < :endDateExclusive
        GROUP BY c.code, c.name
        ORDER BY SUM(d.purchasePrice * d.quantity) DESC
        """)
    List<CategorySpendingProjection> findCategorySpending(
            @Param("startDate") LocalDateTime startDate,
            @Param("endDateExclusive") LocalDateTime endDateExclusive
    );

}
