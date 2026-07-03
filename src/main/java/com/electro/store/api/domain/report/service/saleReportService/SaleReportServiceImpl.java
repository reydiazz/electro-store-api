package com.electro.store.api.domain.report.service.saleReportService;

import com.electro.store.api.domain.report.dto.saleDTO.MonthlySalesDTO;
import com.electro.store.api.domain.report.dto.saleDTO.RankingRevenueDTO;
import com.electro.store.api.domain.report.dto.saleDTO.RankingSellingDTO;
import com.electro.store.api.domain.report.service.ReportService;
import com.electro.store.api.domain.sales.model.entity.Sale;
import com.electro.store.api.domain.sales.repository.SaleRepository;
import com.electro.store.api.domain.sales.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor

public class SaleReportServiceImpl implements SaleReportService{

    private final SaleRepository saleRepository;
    private final SaleService saleService;

    @Override
    public List<MonthlySalesDTO> getMonthlySales(int year){
        List<Sale> sales = saleRepository.findSalesByYear(year);
        List<MonthlySalesDTO> result = new ArrayList<>();

        String[] months = {
                "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio",
                "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
        };

        for (String month : months){
            MonthlySalesDTO dto = new MonthlySalesDTO();
            dto.setMonth(month);
            dto.setTotalRevenue(BigDecimal.ZERO);
            dto.setIncrease(BigDecimal.ZERO);
            dto.setPercentage(0.0);
            result.add(dto);
        }

        for(Sale sale : sales) {
            int month = sale.getSaleDate().getMonthValue();
            MonthlySalesDTO dto = result.get(month - 1);
            dto.setTotalRevenue(dto.getTotalRevenue().add(saleService.calculateSaleTotal(sale)));
        }

        BigDecimal totalYear = BigDecimal.ZERO;

        for (MonthlySalesDTO dto : result) {
            totalYear = totalYear.add(dto.getTotalRevenue());
        }

        if(totalYear.compareTo(BigDecimal.ZERO) > 0) {
            for(MonthlySalesDTO dto : result) {
                double percentage = dto.getTotalRevenue().multiply(BigDecimal.valueOf(100)).
                        divide(totalYear, 2, RoundingMode.HALF_UP).doubleValue();
                dto.setPercentage(percentage);
            }
        }

        for (int i = 1; i < result.size(); i++) {
            BigDecimal previous = result.get(i - 1).getTotalRevenue();
            BigDecimal current = result.get(i).getTotalRevenue();
            if (previous.compareTo(BigDecimal.ZERO) > 0) {
                BigDecimal increase = current
                        .subtract(previous)
                        .multiply(BigDecimal.valueOf(100))
                        .divide(previous, 2, RoundingMode.HALF_UP);
                result.get(i).setIncrease(increase);
            } else {
                result.get(i).setIncrease(BigDecimal.ZERO);
            }
        }
        return result;
    }

    @Override
    public List<RankingRevenueDTO> getTopRevenue(int year){
        return new ArrayList<>();
    }

    @Override
    public List<RankingRevenueDTO> getBottomRevenue(int year){
        return new ArrayList<>();
    }

    @Override
    public List<RankingSellingDTO> getTopSelling(int year){
        return new ArrayList<>();
    }

    @Override
    public List<RankingSellingDTO> getBottomSelling(int year){
        return new ArrayList<>();
    }

}
