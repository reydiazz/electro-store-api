package com.electro.store.api.domain.movement.repository;

import com.electro.store.api.domain.movement.model.entity.InventoryGuide;
import com.electro.store.api.domain.movement.model.enums.GuideType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;

public interface InventoryGuideRepository extends JpaRepository<InventoryGuide, String> {

    @Query("SELECT g FROM InventoryGuide g WHERE " +
            "(:type IS NULL OR g.type = :type) AND " +
            "(:search IS NULL OR :search = '' OR " +
            " LOWER(g.code) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            " LOWER(g.reason) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            " LOWER(g.user.username) LIKE LOWER(CONCAT('%', :search, '%'))) AND " +
            "(:startDate IS NULL OR g.guideDate >= :startDate) AND " +
            "(:endDate IS NULL OR g.guideDate <= :endDate)")
    Page<InventoryGuide> search(
            @Param("search") String search,
            @Param("type") GuideType type,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable
    );
}
