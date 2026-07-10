package com.electro.store.api.domain.sales.web.response;

import java.math.BigDecimal;

public record DashboardProjection(
        BigDecimal totalAmount,
        Long transactionCount
) {}
