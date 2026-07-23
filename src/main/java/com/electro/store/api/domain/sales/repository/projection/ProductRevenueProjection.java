package com.electro.store.api.domain.sales.repository.projection;

import java.math.BigDecimal;

public record ProductRevenueProjection( String productName, String categoryName, BigDecimal totalRevenue) { }
