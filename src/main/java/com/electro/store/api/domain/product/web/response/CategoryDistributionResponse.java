package com.electro.store.api.domain.product.web.response;

public record CategoryDistributionResponse(
        String categoryName,
        long count
) {}
