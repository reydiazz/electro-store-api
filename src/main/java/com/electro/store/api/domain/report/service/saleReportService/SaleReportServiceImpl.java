package com.electro.store.api.domain.report.service.saleReportService;

import com.electro.store.api.domain.product.model.entity.Product;
import com.electro.store.api.domain.report.dto.saleDTO.MonthlySalesDTO;
import com.electro.store.api.domain.report.dto.saleDTO.RankingRevenueDTO;
import com.electro.store.api.domain.report.dto.saleDTO.RankingSellingDTO;
import com.electro.store.api.domain.report.service.ReportService;
import com.electro.store.api.domain.sales.model.entity.Sale;
import com.electro.store.api.domain.sales.model.entity.SaleDetail;
import com.electro.store.api.domain.sales.repository.SaleRepository;
import com.electro.store.api.domain.sales.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.context.annotation.ScopeMetadata;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Map.Entry.comparingByValue;

@Service
@RequiredArgsConstructor

public class SaleReportServiceImpl implements SaleReportService{

    private final SaleRepository saleRepository;
    private final SaleService saleService;

    @Override
    public List<MonthlySalesDTO> getMonthlySales(int year){

        LocalDateTime startDate = LocalDate.of(year, 1, 1).atStartOfDay();
        LocalDateTime endDate = LocalDate.of(year,12,31).atTime(LocalTime.MAX);

        List<Sale> sales = saleRepository.findBySaleDateBetween(startDate, endDate);
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

        MonthlySalesDTO january = result.get(0);
        january.setAbsoluteGrowth(january.getTotalRevenue());
        january.setIncrease(BigDecimal.ZERO);

        for (int i = 1; i < result.size(); i++) {
            BigDecimal previous = result.get(i - 1).getTotalRevenue();
            BigDecimal current = result.get(i).getTotalRevenue();
            BigDecimal growthAbsolute = current.subtract(previous);
            result.get(i).setAbsoluteGrowth(growthAbsolute);

            if (previous.compareTo(BigDecimal.ZERO) > 0) {
                BigDecimal increasePercentage = growthAbsolute
                        .multiply(BigDecimal.valueOf(100))
                        .divide(previous, 2, RoundingMode.HALF_UP);
                result.get(i).setIncrease(increasePercentage);
            } else {
                result.get(i).setIncrease(BigDecimal.ZERO);
            }
        }
        return result;
    }

    @Override
    public List<RankingRevenueDTO> getTopRevenue(int year){

        LocalDateTime startDate = LocalDate.of(year, 1, 1).atStartOfDay();
        LocalDateTime endDate = LocalDate.of(year, 12, 31).atTime(LocalTime.MAX);
        List<Sale> sales = saleRepository.findBySaleDateBetween(startDate, endDate);

        Map<Product, BigDecimal> revenueByProduct = sales.stream()
                .flatMap(sale -> sale.getDetails().stream())
                .collect(Collectors.toMap(
                        SaleDetail::getProduct,
                        detail -> detail.getSalePrice().multiply(BigDecimal.valueOf(detail.getQuantity())),
                        BigDecimal::add
                ));

        return revenueByProduct.entrySet().stream()
                .sorted(Map.Entry.<Product, BigDecimal>comparingByValue().reversed()).limit(5)
                .map(entry -> {
                    Product product = entry.getKey();
                    BigDecimal totalRevenue = entry.getValue();

                    return new RankingRevenueDTO(
                            product.getName(),
                            product.getCategory().getName(),
                            totalRevenue
                    );
                }).toList();
    }

    @Override
    public List<RankingRevenueDTO> getBottomRevenue(int year){

        LocalDateTime startDate = LocalDate.of(year, 1,1).atStartOfDay();
        LocalDateTime endDate = LocalDate.of(year, 12,31).atTime(LocalTime.MAX);
        List<Sale> sales = saleRepository.findBySaleDateBetween(startDate, endDate);

        Map<Product, BigDecimal> revenueByProduct = sales.stream()
                .flatMap(sale -> sale.getDetails().stream())
                .collect(Collectors.toMap(
                        SaleDetail::getProduct,
                        detail -> detail.getSalePrice().multiply(BigDecimal.valueOf(detail.getQuantity())),
                        BigDecimal::add
                ));

        return revenueByProduct.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .limit(5)
                .map(entry -> {
                    Product product = entry.getKey();
                    BigDecimal totalRevenue = entry.getValue();
                    return new RankingRevenueDTO(
                            product.getName(),
                            product.getCategory().getName(),
                            totalRevenue
                    );
                }).toList();
    }

    @Override
    public List<RankingSellingDTO> getTopSelling(int year){
        LocalDateTime startDate = LocalDate.of(year, 1, 1).atStartOfDay();
        LocalDateTime endDate = LocalDate.of(year, 12, 31).atTime(LocalTime.MAX);
        List<Sale> sales = saleRepository.findBySaleDateBetween(startDate, endDate);

        Map<Product, Integer> quantityByProduct = sales.stream()
                .flatMap(sale -> sale.getDetails().stream())
                .collect(Collectors.groupingBy(
                        SaleDetail::getProduct,
                        Collectors.summingInt(SaleDetail::getQuantity)
                ));

        return quantityByProduct.entrySet().stream()
                .sorted(Map.Entry.<Product, Integer>comparingByValue().reversed())
                .limit(5)
                .map(entry -> {
                    Product product = entry.getKey();
                    Integer totalQuantity = entry.getValue();

                    return new RankingSellingDTO(
                            product.getName(),
                            product.getCategory().getName(),
                            totalQuantity
                    );
                })
                .toList();
    }

    @Override
    public List<RankingSellingDTO> getBottomSelling(int year){
        LocalDateTime startDate = LocalDate.of(year, 1, 1).atStartOfDay();
        LocalDateTime endDate = LocalDate.of(year, 12, 31).atTime(LocalTime.MAX);
        List<Sale> sales = saleRepository.findBySaleDateBetween(startDate, endDate);

        Map<Product, Integer> quantityByProduct = sales.stream()
                .flatMap(sale -> sale.getDetails().stream())
                .collect(Collectors.groupingBy(
                        SaleDetail::getProduct,
                        Collectors.summingInt(SaleDetail::getQuantity)
                ));

        return quantityByProduct.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .limit(5)
                .map(entry -> {
                    Product product = entry.getKey();
                    Integer totalQuantity = entry.getValue();

                    return new RankingSellingDTO(
                            product.getName(),
                            product.getCategory().getName(),
                            totalQuantity
                    );
                })
                .toList();
    }

}
