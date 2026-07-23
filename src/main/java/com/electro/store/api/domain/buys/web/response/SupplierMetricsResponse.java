package com.electro.store.api.domain.buys.web.response;

public record SupplierMetricsResponse(
        long totalSuppliers,
        String lastSupplierName
) {
}
