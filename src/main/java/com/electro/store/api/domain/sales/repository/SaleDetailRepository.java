package com.electro.store.api.domain.sales.repository;

import com.electro.store.api.domain.sales.model.entity.SaleDetail;
import com.electro.store.api.domain.sales.repository.projection.SaleMovementProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface SaleDetailRepository extends JpaRepository<SaleDetail, String> {

    @Query("""
        SELECT COALESCE(SUM(d.quantity), 0)
        FROM SaleDetail d
        WHERE d.product.code = :productCode
          AND d.sale.saleDate < :startDate
        """)
    Integer getSoldQuantityBefore(
            @Param("productCode") String productCode,
            @Param("startDate") LocalDateTime startDate
    );

    @Query("""
        SELECT new com.electro.store.api.domain.sales.repository.projection.SaleMovementProjection(
            d.product.code, s.saleDate, s.code,
            CONCAT(pe.firstName, ' ', pe.lastName), d.quantity)
        FROM SaleDetail d
        JOIN d.sale s
        JOIN s.customer c
        JOIN c.person pe
        WHERE s.saleDate <= :endDate
          AND (:productCode IS NULL OR d.product.code = :productCode)
        ORDER BY d.product.code, s.saleDate
        """)
    List<SaleMovementProjection> findSaleMovementsUntil(
            @Param("productCode") String productCode,
            @Param("endDate") LocalDateTime endDate
    );


}
