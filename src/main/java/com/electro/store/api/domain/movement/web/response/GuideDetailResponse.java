package com.electro.store.api.domain.movement.web.response;

import com.electro.store.api.domain.product.web.response.ProductResponse;

public record GuideDetailResponse(
        String code,
        ProductResponse product,
        Integer quantity
) {
}
