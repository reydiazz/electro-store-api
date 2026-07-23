package com.electro.store.api.domain.report.service.sale;

import com.electro.store.api.domain.report.dto.sale.MonthlySalesDTO;
import com.electro.store.api.domain.report.dto.sale.RankingRevenueDTO;
import com.electro.store.api.domain.report.dto.sale.RankingSellingDTO;
import com.electro.store.api.domain.report.model.ReportPeriod;
import com.electro.store.api.domain.report.model.enums.ReportFrequency;

import java.util.List;

public interface SaleReportService {

    List<MonthlySalesDTO> getSalesAnalysis(ReportFrequency frequency, ReportPeriod period);
    SaleRanking getSaleRankings(ReportPeriod period);

}
