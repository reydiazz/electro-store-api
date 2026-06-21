package com.electro.store.api.domain.product.web.response;

public record ProductMetricsResponse(
        long totalProducts,
        long lowStockCount,
        long outOfStockCount,
        long totalCategories
) {
}
