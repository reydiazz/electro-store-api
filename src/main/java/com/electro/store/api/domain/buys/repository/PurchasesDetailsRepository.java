package com.electro.store.api.domain.buys.repository;

import com.electro.store.api.domain.buys.model.entity.PurchasesDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

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

}
