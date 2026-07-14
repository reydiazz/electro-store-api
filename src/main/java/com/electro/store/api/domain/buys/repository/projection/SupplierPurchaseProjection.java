package com.electro.store.api.domain.buys.repository.projection;

import java.math.BigDecimal;

public record SupplierPurchaseProjection(
        String supplierName,
        Long purchaseCount,
        BigDecimal totalAmount
) {
}
