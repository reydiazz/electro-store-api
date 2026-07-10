package com.electro.store.api.domain.sales.web.response;

import java.math.BigDecimal;

public record DailySummaryResponse(
        String date,
        BigDecimal total
) {}
