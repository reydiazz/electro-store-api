package com.electro.store.api.domain.movement.web.request;

import com.electro.store.api.domain.movement.model.enums.GuideType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateInventoryGuideRequest(

        @NotNull(message = "Guide type is required")
        GuideType type,

        String reason,
        String description,

        @Valid
        @NotEmpty(message = "Guide details are required")
        List<CreateGuideDetailRequest> details
) {
}
