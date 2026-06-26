package com.electro.store.api.domain.sales.web.response;

import java.math.BigDecimal;

public record SalesDashboardResponse(
        BigDecimal todaySales,
        Long transactions,
        BigDecimal averageTicket
) {
}
