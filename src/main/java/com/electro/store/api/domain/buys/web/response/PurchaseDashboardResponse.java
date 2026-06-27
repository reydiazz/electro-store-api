package com.electro.store.api.domain.buys.web.response;

import java.math.BigDecimal;

public record PurchaseDashboardResponse(
        BigDecimal todayPurchases,
        Long transactions,
        BigDecimal averageTicket
) {
}
