package com.electro.store.api.domain.report.service.sale;

import com.electro.store.api.domain.report.dto.sale.MonthlySalesDTO;
import com.electro.store.api.domain.report.dto.sale.RankingRevenueDTO;
import com.electro.store.api.domain.report.dto.sale.RankingSellingDTO;

import java.util.List;

public interface SaleReportService {

    List<MonthlySalesDTO> getMonthlySales(int year);
    SaleRanking getSaleRankings(int year);

}
