package com.electro.store.api.domain.movement.repository.projection;

import com.electro.store.api.domain.movement.model.enums.GuideType;

import java.time.LocalDateTime;

public record GuideMovementProjection(
        String productCode,
        LocalDateTime date,
        String documentCode,
        GuideType type,
        String reason,
        Integer quantity
) {
}
