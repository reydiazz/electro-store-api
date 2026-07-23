package com.electro.store.api.domain.sales.repository.projection;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record SaleTotalProjection(
        LocalDateTime date,
        BigDecimal totalAmount
) {
}
