package com.electro.store.api.domain.report.service.sale;

import com.electro.store.api.domain.report.dto.sale.MonthlySalesDTO;
import com.electro.store.api.domain.report.dto.sale.RankingRevenueDTO;
import com.electro.store.api.domain.report.dto.sale.RankingSellingDTO;
import com.electro.store.api.domain.sales.repository.SaleRepository;
import com.electro.store.api.domain.sales.repository.projection.MonthlyRevenueProjection;
import com.electro.store.api.domain.sales.repository.projection.ProductQuantityProjection;
import com.electro.store.api.domain.sales.repository.projection.ProductRevenueProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor

public class SaleReportServiceImpl implements SaleReportService{

    private static final int ranking_size = 5;
    private static final String[] month = {
            "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio",
            "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
    };
    private final SaleRepository saleRepository;

    @Override
    @Transactional(readOnly = true)
    public List<MonthlySalesDTO> getMonthlySales(int year) {

        List<MonthlyRevenueProjection> revenueByMonth = saleRepository
                .findMonthlyRevenue(startOfYear(year), startOfNextYear(year));

        List<MonthlySalesDTO> result = new ArrayList<>(12);
        for (String monthName : month) {
            result.add(new MonthlySalesDTO(monthName, BigDecimal.ZERO,
                    BigDecimal.ZERO, BigDecimal.ZERO, 0.0));
        }

        for (MonthlyRevenueProjection projection : revenueByMonth) {
            result.get(projection.month() - 1)
                    .setTotalRevenue(projection.totalRevenue());
        }

        BigDecimal totalYear = result.stream()
                .map(MonthlySalesDTO::getTotalRevenue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (totalYear.compareTo(BigDecimal.ZERO) > 0) {
            for (MonthlySalesDTO dto : result) {
                double percentage = dto.getTotalRevenue()
                        .multiply(BigDecimal.valueOf(100))
                        .divide(totalYear, 2, RoundingMode.HALF_UP)
                        .doubleValue();
                dto.setPercentage(percentage);
            }
        }
        result.get(0).setAbsoluteGrowth(result.get(0).getTotalRevenue());

        for (int i = 1; i < result.size(); i++) {
            BigDecimal previous = result.get(i - 1).getTotalRevenue();
            BigDecimal current = result.get(i).getTotalRevenue();
            BigDecimal absoluteGrowth = current.subtract(previous);
            result.get(i).setAbsoluteGrowth(absoluteGrowth);
            if (previous.compareTo(BigDecimal.ZERO) > 0) {
                result.get(i).setIncrease(absoluteGrowth
                        .multiply(BigDecimal.valueOf(100))
                        .divide(previous, 2, RoundingMode.HALF_UP));
            }
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public SaleRanking getSaleRankings(int year) {

        List<ProductRevenueProjection> byRevenue = saleRepository
                .findProductRevenueRanking(startOfYear(year), startOfNextYear(year));

        List<ProductQuantityProjection> byQuantity = saleRepository
                .findProductQuantityRanking(startOfYear(year), startOfNextYear(year));

        List<RankingRevenueDTO> topRevenue = head(byRevenue).stream()
                .map(this::toRevenueDTO)
                .toList();

        List<RankingRevenueDTO> bottomRevenue = tailReversed(byRevenue).stream()
                .map(this::toRevenueDTO)
                .toList();

        List<RankingSellingDTO> topSelling = head(byQuantity).stream()
                .map(this::toSellingDTO)
                .toList();

        List<RankingSellingDTO> bottomSelling = tailReversed(byQuantity).stream()
                .map(this::toSellingDTO)
                .toList();

        return new SaleRanking(topRevenue, bottomRevenue, topSelling, bottomSelling);
    }

    private LocalDateTime startOfYear(int year) {
        return LocalDate.of(year, 1, 1).atStartOfDay();
    }

    private LocalDateTime startOfNextYear(int year) {
        return LocalDate.of(year + 1, 1, 1).atStartOfDay();
    }

    private <T> List<T> head(List<T> ranking) {
        return ranking.subList(0, Math.min(ranking_size, ranking.size()));
    }

    private <T> List<T> tailReversed(List<T> ranking) {
        int from = Math.max(0, ranking.size() - ranking_size);
        return ranking.subList(from, ranking.size()).reversed();
    }

    private RankingRevenueDTO toRevenueDTO(ProductRevenueProjection projection) {
        return new RankingRevenueDTO(
                projection.productName(),
                projection.categoryName(),
                projection.totalRevenue()
        );
    }

    private RankingSellingDTO toSellingDTO(ProductQuantityProjection projection) {
        return new RankingSellingDTO(
                projection.productName(),
                projection.categoryName(),
                projection.totalQuantity().intValue()
        );
    }
}
