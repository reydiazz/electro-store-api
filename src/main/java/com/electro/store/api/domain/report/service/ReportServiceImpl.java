package com.electro.store.api.domain.report.service;

import com.electro.store.api.domain.report.dto.saleDTO.MonthlySalesDTO;
import com.electro.store.api.domain.report.dto.saleDTO.RankingRevenueDTO;
import com.electro.store.api.domain.report.dto.saleDTO.RankingSellingDTO;
import com.electro.store.api.domain.report.service.saleReportService.SaleReportService;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class ReportServiceImpl implements ReportService {

    private final SaleReportService saleReportService;

    @Override
    public byte[] generatePdfSalesReport(int year) throws Exception {

        List<MonthlySalesDTO> monthlySales = saleReportService.getMonthlySales(year);
        List<RankingRevenueDTO> topRevenue = saleReportService.getTopRevenue(year);
        List<RankingRevenueDTO> bottomRevenue = saleReportService.getBottomRevenue(year);
        List<RankingSellingDTO> topSelling = saleReportService.getTopSelling(year);
        List<RankingSellingDTO> bottomSelling = saleReportService.getBottomSelling(year);

        InputStream reportStream = new ClassPathResource("reports/ReporteVenta.jrxml").getInputStream();
        InputStream logoStream = new ClassPathResource("reports/logo.png").getInputStream();
        JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

        Map<String, Object> parametros = new HashMap<>();
        parametros.put("logoEmpresa", logoStream);

        parametros.put("dsVentasMensuales", new JRBeanCollectionDataSource(monthlySales));
        parametros.put("dsTopIngresos", new JRBeanCollectionDataSource(topRevenue));
        parametros.put("dsBottomIngresos", new JRBeanCollectionDataSource(bottomRevenue));
        parametros.put("dsTopVendidos", new JRBeanCollectionDataSource(topSelling));
        parametros.put("dsBottomVendidos", new JRBeanCollectionDataSource(bottomSelling));

        List<Map<String, Object>> chartTopRevenue = topRevenue.stream().map(dto -> {
            Map<String, Object> map = new HashMap<>();
            map.put("productName", dto.getNameProduct());
            map.put("totalRevenue", dto.getSaleByTotalProduct());
            return map;
        }).collect(Collectors.toList());

        List<Map<String, Object>> chartBottomRevenue = bottomRevenue.stream().map(dto -> {
            Map<String, Object> map = new HashMap<>();
            map.put("productName", dto.getNameProduct());
            map.put("totalRevenue", dto.getSaleByTotalProduct());
            return map;
        }).collect(Collectors.toList());

        List<Map<String, Object>> chartTopSelling = topSelling.stream().map(dto -> {
            Map<String, Object> map = new HashMap<>();
            map.put("productName", dto.getNameProduct());
            map.put("quantity", dto.getQuantity());
            return map;
        }).collect(Collectors.toList());

        List<Map<String, Object>> chartBottomSelling = bottomSelling.stream().map(dto -> {
            Map<String, Object> map = new HashMap<>();
            map.put("productName", dto.getNameProduct());
            map.put("quantity", dto.getQuantity());
            return map;
        }).collect(Collectors.toList());

        List<Map<String, Object>> chartMonthlySales = monthlySales.stream().map(dto -> {
            Map<String, Object> map = new HashMap<>();
            map.put("monthName", dto.getMonth());
            map.put("totalSales", dto.getTotalRevenue());
            return map;
        }).collect(Collectors.toList());

        parametros.put("dsGraficoTopIngresos", new JRBeanCollectionDataSource(chartTopRevenue));
        parametros.put("dsGraficoBottomIngresos", new JRBeanCollectionDataSource(chartBottomRevenue));
        parametros.put("dsGraficoTopVendidos", new JRBeanCollectionDataSource(chartTopSelling));
        parametros.put("dsGraficoBottomVendidos", new JRBeanCollectionDataSource(chartBottomSelling));
        parametros.put("dsGraficoVentasMensuales", new JRBeanCollectionDataSource(chartMonthlySales));

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, new JREmptyDataSource());

        return JasperExportManager.exportReportToPdf(jasperPrint);
    }
}
