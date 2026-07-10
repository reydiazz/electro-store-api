package com.electro.store.api.domain.sales.web.response;

public record TopProductResponse(
        String productName,
        long totalQuantity
) {}
