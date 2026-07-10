package com.electro.store.api.domain.sales.repository;

import com.electro.store.api.domain.sales.model.entity.SaleDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

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

}
