package com.electro.store.api.domain.movement.repository;

import com.electro.store.api.domain.movement.model.entity.GuideDetail;
import com.electro.store.api.domain.movement.model.enums.GuideType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface GuideDetailRepository extends JpaRepository<GuideDetail, String> {
    @Query("""
        SELECT COALESCE(SUM(d.quantity), 0)
        FROM GuideDetail d
        WHERE d.product.code = :productCode
          AND d.guide.guideDate < :startDate
          AND d.guide.type = :type
        """)
    Integer getGuideQuantityBefore(
            @Param("productCode") String productCode,
            @Param("startDate") LocalDateTime startDate,
            @Param("type") GuideType type
    );

}
