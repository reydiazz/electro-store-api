package com.electro.store.api.domain.buys.web.response;

import java.math.BigDecimal;

public record PurchaseSummaryResponse(
        BigDecimal subtotal,
        BigDecimal igv,
        BigDecimal total
) {
}
