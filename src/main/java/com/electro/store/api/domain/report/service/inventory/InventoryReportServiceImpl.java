package com.electro.store.api.domain.report.service.inventory;

import com.electro.store.api.domain.buys.repository.PurchasesDetailsRepository;
import com.electro.store.api.domain.movement.model.enums.GuideType;
import com.electro.store.api.domain.movement.repository.GuideDetailRepository;
import com.electro.store.api.domain.product.repository.ProductRepository;
import com.electro.store.api.domain.product.repository.projection.InventoryItemProjection;
import com.electro.store.api.domain.report.dto.inventory.InventoryCategoryValueDTO;
import com.electro.store.api.domain.report.dto.inventory.InventoryTrendDTO;
import com.electro.store.api.domain.report.dto.inventory.ProductRotationDTO;
import com.electro.store.api.domain.report.model.ReportPeriod;
import com.electro.store.api.domain.sales.repository.SaleDetailRepository;
import com.electro.store.api.domain.sales.repository.SaleRepository;
import com.electro.store.api.domain.sales.repository.projection.ProductQuantityProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.*;

@Service
@RequiredArgsConstructor
public class InventoryReportServiceImpl implements InventoryReportService {

    private static final int ROTATION_SIZE = 5;
    private static final int TREND_MONTHS = 6;

    private static final String[] SHORT_MONTHS = {
            "Ene", "Feb", "Mar", "Abr", "May", "Jun",
            "Jul", "Ago", "Sep", "Oct", "Nov", "Dic"
    };

    private final ProductRepository productRepository;
    private final SaleRepository saleRepository;
    private final SaleDetailRepository saleDetailRepository;
    private final PurchasesDetailsRepository purchasesDetailsRepository;
    private final GuideDetailRepository guideDetailRepository;

    @Override
    @Transactional(readOnly = true)
    public InventoryReportData getInventoryReportData(ReportPeriod rotationPeriod) {

        List<InventoryItemProjection> snapshot = productRepository.findInventorySnapshot();

        long totalUnits = 0;
        BigDecimal totalValue = BigDecimal.ZERO;
        long lowStockCount = 0;
        long outOfStockCount = 0;
        Map<String, BigDecimal> valueByCategory = new LinkedHashMap<>();

        for (InventoryItemProjection product : snapshot) {
            BigDecimal itemValue = product.salePrice()
                    .multiply(BigDecimal.valueOf(product.stock()));
            totalUnits += product.stock();
            totalValue = totalValue.add(itemValue);
            if (product.stock() == 0) {
                outOfStockCount++;
            } else if (product.stock() <= product.lowStock()) {
                lowStockCount++;
            }
            valueByCategory.merge(product.categoryName(), itemValue, BigDecimal::add);
        }

        List<InventoryCategoryValueDTO> categoryValues = valueByCategory.entrySet().stream()
                .map(entry -> new InventoryCategoryValueDTO(entry.getKey(), entry.getValue()))
                .sorted(Comparator.comparing(InventoryCategoryValueDTO::getTotalValue).reversed())
                .toList();

        RotationRankings rotation = buildRotationRankings(snapshot, rotationPeriod);
        List<InventoryTrendDTO> trend = buildTrend(totalUnits);

        return new InventoryReportData(
                snapshot.size(),
                totalUnits,
                totalValue,
                lowStockCount,
                outOfStockCount,
                categoryValues,
                trend,
                rotation.top(),
                rotation.bottom()
        );
    }

    private record RotationRankings(List<ProductRotationDTO> top, List<ProductRotationDTO> bottom) {
    }

    private RotationRankings buildRotationRankings(
            List<InventoryItemProjection> snapshot, ReportPeriod rotationPeriod) {

        List<ProductQuantityProjection> soldRanking = saleRepository
                .findProductQuantityRanking(rotationPeriod.start(), rotationPeriod.endExclusive());

        Map<String, Long> soldByProduct = new HashMap<>();
        for (ProductQuantityProjection sold : soldRanking) {
            soldByProduct.merge(sold.productName(), sold.totalQuantity(), Long::sum);
        }

        List<ProductRotationDTO> allProducts = snapshot.stream()
                .map(product -> new ProductRotationDTO(
                        product.productName(),
                        soldByProduct.getOrDefault(product.productName(), 0L),
                        product.stock()))
                .toList();

        List<ProductRotationDTO> top = allProducts.stream()
                .filter(product -> product.getSoldQuantity() > 0)
                .sorted(Comparator.comparing(ProductRotationDTO::getSoldQuantity).reversed())
                .limit(ROTATION_SIZE)
                .toList();

        List<ProductRotationDTO> bottom = allProducts.stream()
                .sorted(Comparator.comparing(ProductRotationDTO::getSoldQuantity)
                        .thenComparing(ProductRotationDTO::getStock, Comparator.reverseOrder()))
                .limit(ROTATION_SIZE)
                .toList();
        return new RotationRankings(top, bottom);
    }

    private List<InventoryTrendDTO> buildTrend(long currentUnits) {

        LocalDateTime now = LocalDateTime.now();
        Map<YearMonth, Long> netByMonth = new HashMap<>();

        purchasesDetailsRepository.findPurchaseMovementsUntil(null, now).forEach(purchase ->
                netByMonth.merge(YearMonth.from(purchase.date()),
                        purchase.quantity().longValue(), Long::sum));

        saleDetailRepository.findSaleMovementsUntil(null, now).forEach(sale ->
                netByMonth.merge(YearMonth.from(sale.date()),
                        -sale.quantity().longValue(), Long::sum));

        guideDetailRepository.findGuideMovementsUntil(null, now).forEach(guide -> {
            long signed = guide.type() == GuideType.ENTRY
                    ? guide.quantity().longValue()
                    : -guide.quantity().longValue();
            netByMonth.merge(YearMonth.from(guide.date()), signed, Long::sum);
        });

        YearMonth currentMonth = YearMonth.from(now);
        long units = currentUnits;
        List<InventoryTrendDTO> reversedTrend = new ArrayList<>(TREND_MONTHS);

        for (int i = 0; i < TREND_MONTHS; i++) {
            YearMonth month = currentMonth.minusMonths(i);
            String label = SHORT_MONTHS[month.getMonthValue() - 1] + " " + (month.getYear() % 100);
            reversedTrend.add(new InventoryTrendDTO(label, Math.max(units, 0)));
            units -= netByMonth.getOrDefault(month, 0L);
        }

        return reversedTrend.reversed();
    }
}
