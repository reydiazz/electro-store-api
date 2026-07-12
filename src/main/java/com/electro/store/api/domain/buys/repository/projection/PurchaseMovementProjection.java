package com.electro.store.api.domain.buys.repository.projection;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PurchaseMovementProjection(
        String productCode,
        LocalDateTime date,
        String documentCode,
        String supplierName,
        Integer quantity,
        BigDecimal unitCost
) {
}
