package com.electro.store.api.domain.buys.repository.projection;

import java.math.BigDecimal;

public record ProductPurchaseProjection(
        String productName,
        Long quantity,
        BigDecimal totalAmount
) {
}
