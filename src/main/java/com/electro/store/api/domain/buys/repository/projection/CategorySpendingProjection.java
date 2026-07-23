package com.electro.store.api.domain.buys.repository.projection;

import java.math.BigDecimal;

public record CategorySpendingProjection(
        String categoryName,
        BigDecimal totalAmount
) {
}
