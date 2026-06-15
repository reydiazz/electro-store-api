package com.electro.store.api.domain.sales.web.response;

import com.electro.store.api.domain.product.web.response.ProductResponse;

import java.math.BigDecimal;

public record SaleDetailResponse(
        String code,
        ProductResponse product,
        BigDecimal salePrice,
        Integer quantity
) {
}
