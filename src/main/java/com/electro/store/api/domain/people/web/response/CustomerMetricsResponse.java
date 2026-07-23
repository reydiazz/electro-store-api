package com.electro.store.api.domain.people.web.response;

public record CustomerMetricsResponse(
        long totalCustomers,
        long totalWithDni,
        long totalWithRuc
) {
}
