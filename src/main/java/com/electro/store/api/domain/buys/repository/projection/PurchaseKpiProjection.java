package com.electro.store.api.domain.buys.repository.projection;

import java.math.BigDecimal;

public record PurchaseKpiProjection(
        Long purchaseCount,
        Long totalUnits,
        BigDecimal totalInvested
) {
}
