package com.electro.store.api.domain.sales.repository.projection;

import java.time.LocalDateTime;

public record SaleMovementProjection(
        String productCode,
        LocalDateTime date,
        String documentCode,
        String customerName,
        Integer quantity
) {
}
