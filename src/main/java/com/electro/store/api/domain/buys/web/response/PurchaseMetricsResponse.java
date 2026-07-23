package com.electro.store.api.domain.buys.web.response;

public record PurchaseMetricsResponse(
        long weeklyPurchases,
        long monthlyProductsEntered,
        String frequentSupplierName,
        long frequentSupplierCount
) {
}