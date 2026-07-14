package com.electro.store.api.domain.buys.repository.projection;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PurchaseTotalProjection(
        LocalDateTime date,
        BigDecimal totalAmount
) {
}
