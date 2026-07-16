package com.electro.store.api.domain.product.repository.projection;

import java.math.BigDecimal;

public record InventoryItemProjection(
        String categoryName,
        String productCode,
        String productName,
        String brand,
        String model,
        Integer stock,
        Integer lowStock,
        BigDecimal salePrice
) {
}
