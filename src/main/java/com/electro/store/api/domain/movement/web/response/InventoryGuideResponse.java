package com.electro.store.api.domain.movement.web.response;

import com.electro.store.api.domain.auth.web.response.UserResponse;
import com.electro.store.api.domain.movement.model.enums.GuideType;

import java.time.LocalDateTime;
import java.util.List;

public record InventoryGuideResponse(
        String code,
        UserResponse user,
        GuideType type,
        String reason,
        String description,
        LocalDateTime guideDate,
        List<GuideDetailResponse> details
) {
}
