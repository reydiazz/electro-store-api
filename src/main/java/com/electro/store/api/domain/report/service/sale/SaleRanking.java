package com.electro.store.api.domain.report.service.sale;

import com.electro.store.api.domain.report.dto.sale.RankingRevenueDTO;
import com.electro.store.api.domain.report.dto.sale.RankingSellingDTO;

import java.util.List;

public record SaleRanking(
        List<RankingRevenueDTO> topRevenue,
        List<RankingRevenueDTO> bottomRevenue,
        List<RankingSellingDTO> topSelling,
        List<RankingSellingDTO> bottomSelling
) {
}
