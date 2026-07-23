package com.electro.store.api.domain.buys.web.response;

import java.math.BigDecimal;

public record PurchaseDashboardProjection(
        BigDecimal totalAmount,
        Long transactionCount
) {}
