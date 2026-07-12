package com.electro.store.api.domain.buys.repository;

import com.electro.store.api.domain.buys.model.entity.PurchasesDetails;
import com.electro.store.api.domain.buys.repository.projection.PurchaseMovementProjection;
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

}
