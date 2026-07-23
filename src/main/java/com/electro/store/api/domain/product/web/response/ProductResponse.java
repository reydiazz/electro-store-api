package com.electro.store.api.domain.product.web.response;

import java.math.BigDecimal;

public record ProductResponse(
        String code,
        String categoryName,
        String name,
        String brand,
        String model,
        BigDecimal salePrice,
        Integer stock,
        String description,
        Integer warrantyMonths,
        Integer lowStock
) {
}
