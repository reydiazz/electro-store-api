package com.electro.store.api.domain.sales.repository.projection;

import java.math.BigDecimal;

public record MonthlyRevenueProjection( Integer month, BigDecimal totalRevenue) { }
