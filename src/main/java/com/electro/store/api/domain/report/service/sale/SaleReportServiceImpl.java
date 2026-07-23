package com.electro.store.api.domain.report.service.sale;

import com.electro.store.api.domain.report.dto.sale.MonthlySalesDTO;
import com.electro.store.api.domain.report.dto.sale.RankingRevenueDTO;
import com.electro.store.api.domain.report.dto.sale.RankingSellingDTO;
import com.electro.store.api.domain.report.model.PeriodGrafic;
import com.electro.store.api.domain.report.model.ReportPeriod;
import com.electro.store.api.domain.report.model.enums.ReportFrequency;
import com.electro.store.api.domain.sales.repository.SaleRepository;
import com.electro.store.api.domain.sales.repository.projection.ProductQuantityProjection;
import com.electro.store.api.domain.sales.repository.projection.ProductRevenueProjection;
import com.electro.store.api.domain.sales.repository.projection.SaleTotalProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SaleReportServiceImpl implements SaleReportService {

    private static final int RANKING_SIZE = 5;

    private final SaleRepository saleRepository;

    @Override
    @Transactional(readOnly = true)
    public List<MonthlySalesDTO> getSalesAnalysis(ReportFrequency frequency, ReportPeriod period) {

        List<SaleTotalProjection> totals = saleRepository
                .findSaleTotals(period.start(), period.endExclusive());

        LinkedHashMap<String, BigDecimal> buckets = PeriodGrafic.zeroSkeleton(frequency, period);
        for (SaleTotalProjection total : totals) {
            buckets.merge(PeriodGrafic.keyOf(frequency, total.date()),
                    total.totalAmount(), BigDecimal::add);
        }

        List<MonthlySalesDTO> result = new ArrayList<>(buckets.size());
        buckets.forEach((label, revenue) ->
                result.add(new MonthlySalesDTO(label, revenue,
                        BigDecimal.ZERO, BigDecimal.ZERO, 0.0)));

        BigDecimal periodTotal = result.stream()
                .map(MonthlySalesDTO::getTotalRevenue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (periodTotal.compareTo(BigDecimal.ZERO) > 0) {
            for (MonthlySalesDTO dto : result) {
                dto.setPercentage(dto.getTotalRevenue()
                        .multiply(BigDecimal.valueOf(100))
                        .divide(periodTotal, 2, RoundingMode.HALF_UP)
                        .doubleValue());
            }
        }

        if (!result.isEmpty()) {
            result.get(0).setAbsoluteGrowth(result.get(0).getTotalRevenue());
        }
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
    public SaleRanking getSaleRankings(ReportPeriod period) {

        List<ProductRevenueProjection> byRevenue = saleRepository
                .findProductRevenueRanking(period.start(), period.endExclusive());

        List<ProductQuantityProjection> byQuantity = saleRepository
                .findProductQuantityRanking(period.start(), period.endExclusive());

        List<RankingRevenueDTO> topRevenue = head(byRevenue).stream()
                .map(this::toRevenueDTO).toList();
        List<RankingRevenueDTO> bottomRevenue = tailReversed(byRevenue).stream()
                .map(this::toRevenueDTO).toList();
        List<RankingSellingDTO> topSelling = head(byQuantity).stream()
                .map(this::toSellingDTO).toList();
        List<RankingSellingDTO> bottomSelling = tailReversed(byQuantity).stream()
                .map(this::toSellingDTO).toList();

        return new SaleRanking(topRevenue, bottomRevenue, topSelling, bottomSelling);
    }

    private <T> List<T> head(List<T> ranking) {
        return ranking.subList(0, Math.min(RANKING_SIZE, ranking.size()));
    }

    private <T> List<T> tailReversed(List<T> ranking) {
        int from = Math.max(0, ranking.size() - RANKING_SIZE);
        return ranking.subList(from, ranking.size()).reversed();
    }

    private RankingRevenueDTO toRevenueDTO(ProductRevenueProjection projection) {
        return new RankingRevenueDTO(projection.productName(),
                projection.categoryName(), projection.totalRevenue());
    }

    private RankingSellingDTO toSellingDTO(ProductQuantityProjection projection) {
        return new RankingSellingDTO(projection.productName(),
                projection.categoryName(), projection.totalQuantity().intValue());
    }
}
