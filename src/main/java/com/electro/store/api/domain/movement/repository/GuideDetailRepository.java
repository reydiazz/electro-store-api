package com.electro.store.api.domain.movement.repository;

import com.electro.store.api.domain.movement.model.entity.GuideDetail;
import com.electro.store.api.domain.movement.model.enums.GuideType;
import com.electro.store.api.domain.movement.repository.projection.GuideMovementProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

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

    @Query("""
        SELECT new com.electro.store.api.domain.movement.repository.projection.GuideMovementProjection(
            d.product.code, g.guideDate, g.code, g.type, g.reason, d.quantity)
        FROM GuideDetail d
        JOIN d.guide g
        WHERE g.guideDate <= :endDate
          AND (:productCode IS NULL OR d.product.code = :productCode)
        ORDER BY d.product.code, g.guideDate
        """)
    List<GuideMovementProjection> findGuideMovementsUntil(
            @Param("productCode") String productCode,
            @Param("endDate") LocalDateTime endDate
    );

}
