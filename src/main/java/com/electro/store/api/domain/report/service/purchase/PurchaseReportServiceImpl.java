package com.electro.store.api.domain.report.service.purchase;

import com.electro.store.api.domain.buys.repository.PurchasesDetailsRepository;
import com.electro.store.api.domain.buys.repository.projection.PurchaseKpiProjection;
import com.electro.store.api.domain.buys.repository.projection.PurchaseTotalProjection;
import com.electro.store.api.domain.report.dto.purchase.CategorySpendingDTO;
import com.electro.store.api.domain.report.dto.purchase.PurchaseEvolutionDTO;
import com.electro.store.api.domain.report.dto.purchase.TopPurchasedProductDTO;
import com.electro.store.api.domain.report.dto.purchase.TopSupplierDTO;
import com.electro.store.api.domain.report.model.ReportPeriod;
import com.electro.store.api.domain.report.model.enums.ReportFrequency;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PurchaseReportServiceImpl implements PurchaseReportService {

    private static final int RANKING_SIZE = 5;
    private static final Locale SPANISH = Locale.of("es", "PE");
    private static final String[] SHORT_MONTHS = {
            "Ene", "Feb", "Mar", "Abr", "May", "Jun",
            "Jul", "Ago", "Sep", "Oct", "Nov", "Dic"
    };
    private final PurchasesDetailsRepository purchasesDetailsRepository;

    @Override
    @Transactional(readOnly = true)
    public PurchaseReportData getPurchaseReportData(ReportFrequency frequency, ReportPeriod period) {
        PurchaseKpiProjection kpis = purchasesDetailsRepository
                .findPurchaseKpis(period.start(), period.endExclusive());
        List<TopSupplierDTO> suppliers = purchasesDetailsRepository
                .findSupplierRanking(period.start(), period.endExclusive()).stream()
                .map(s -> new TopSupplierDTO(s.supplierName(), s.purchaseCount(), s.totalAmount()))
                .toList();
        List<TopPurchasedProductDTO> products = purchasesDetailsRepository
                .findProductPurchaseRanking(period.start(), period.endExclusive()).stream()
                .map(p -> new TopPurchasedProductDTO(p.productName(), p.quantity(), p.totalAmount()))
                .toList();
        List<CategorySpendingDTO> categories = purchasesDetailsRepository
                .findCategorySpending(period.start(), period.endExclusive()).stream()
                .map(c -> new CategorySpendingDTO(c.categoryName(), c.totalAmount()))
                .toList();
        List<PurchaseEvolutionDTO> evolution = buildEvolution(frequency, period,
                purchasesDetailsRepository.findPurchaseTotals(period.start(), period.endExclusive()));
        return new PurchaseReportData(
                kpis.totalInvested() != null ? kpis.totalInvested() : BigDecimal.ZERO,
                kpis.purchaseCount() != null ? kpis.purchaseCount() : 0L,
                kpis.totalUnits() != null ? kpis.totalUnits() : 0L,
                suppliers.isEmpty() ? "—" : suppliers.get(0).getSupplierName(),
                evolution,
                head(suppliers),
                head(products),
                categories
        );
    }

    private List<PurchaseEvolutionDTO> buildEvolution(
            ReportFrequency frequency,
            ReportPeriod period,
            List<PurchaseTotalProjection> totals
    ) {
        Map<String, BigDecimal> buckets = new LinkedHashMap<>();
        LocalDate startDay = period.start().toLocalDate();
        switch (frequency) {
            case ANNUAL -> {
                for (String month : SHORT_MONTHS) {
                    buckets.put(month, BigDecimal.ZERO);
                }
            }
            case MONTHLY -> {
                int days = startDay.lengthOfMonth();
                for (int day = 1; day <= days; day++) {
                    buckets.put(String.valueOf(day), BigDecimal.ZERO);
                }
            }
            case WEEKLY -> {
                DateTimeFormatter shortDate = DateTimeFormatter.ofPattern("dd/MM");
                for (int i = 0; i < 7; i++) {
                    LocalDate day = startDay.plusDays(i);
                    buckets.put(weekLabel(day, shortDate), BigDecimal.ZERO);
                }
            }
            case DAILY -> {
                for (int hour = 0; hour < 24; hour++) {
                    buckets.put(String.format("%02d:00", hour), BigDecimal.ZERO);
                }
            }
        }
        DateTimeFormatter shortDate = DateTimeFormatter.ofPattern("dd/MM");
        for (PurchaseTotalProjection total : totals) {
            String key = switch (frequency) {
                case ANNUAL -> SHORT_MONTHS[total.date().getMonthValue() - 1];
                case MONTHLY -> String.valueOf(total.date().getDayOfMonth());
                case WEEKLY -> weekLabel(total.date().toLocalDate(), shortDate);
                case DAILY -> String.format("%02d:00", total.date().getHour());
            };
            buckets.merge(key, total.totalAmount(), BigDecimal::add);
        }
        return buckets.entrySet().stream()
                .map(entry -> new PurchaseEvolutionDTO(entry.getKey(), entry.getValue()))
                .toList();
    }

    private String weekLabel(LocalDate day, DateTimeFormatter shortDate) {
        String dayName = day.getDayOfWeek().getDisplayName(TextStyle.SHORT, SPANISH);
        return dayName + " " + day.format(shortDate);
    }

    private <T> List<T> head(List<T> ranking) {
        return ranking.subList(0, Math.min(RANKING_SIZE, ranking.size()));
    }

}
