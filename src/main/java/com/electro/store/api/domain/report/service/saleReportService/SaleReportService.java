package com.electro.store.api.domain.report.service.saleReportService;

import com.electro.store.api.domain.report.dto.saleDTO.MonthlySalesDTO;
import com.electro.store.api.domain.report.dto.saleDTO.RankingRevenueDTO;
import com.electro.store.api.domain.report.dto.saleDTO.RankingSellingDTO;

import java.util.List;

public interface SaleReportService {

    List<MonthlySalesDTO> getMonthlySales(int year);
    List<RankingRevenueDTO> getTopRevenue(int year);
    List<RankingRevenueDTO> getBottomRevenue(int year);
    List<RankingSellingDTO> getTopSelling(int year);
    List<RankingSellingDTO> getBottomSelling(int year);

}
