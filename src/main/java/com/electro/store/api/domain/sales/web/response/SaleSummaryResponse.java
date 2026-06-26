package com.electro.store.api.domain.sales.web.response;

import java.math.BigDecimal;

public record SaleSummaryResponse (
        BigDecimal subtotal,
        BigDecimal igv,
        BigDecimal total
) {
}
