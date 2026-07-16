package com.electro.store.api.domain.report.service;

import com.electro.store.api.domain.report.component.JasperPdfGenerator;
import com.electro.store.api.domain.report.dto.kardex.KardexItemDTO;
import com.electro.store.api.domain.report.dto.sale.MonthlySalesDTO;
import com.electro.store.api.domain.report.exception.InvalidReportDateRangeException;
import com.electro.store.api.domain.report.exception.InvalidReportYearException;
import com.electro.store.api.domain.report.exception.ReportGenerationException;
import com.electro.store.api.domain.report.model.ReportPeriod;
import com.electro.store.api.domain.report.model.enums.ReportFrequency;
import com.electro.store.api.domain.report.service.inventory.InventoryReportData;
import com.electro.store.api.domain.report.service.inventory.InventoryReportService;
import com.electro.store.api.domain.report.service.kardex.KardexReportService;
import com.electro.store.api.domain.report.service.purchase.PurchaseReportData;
import com.electro.store.api.domain.report.service.purchase.PurchaseReportService;
import com.electro.store.api.domain.report.service.sale.SaleRanking;
import com.electro.store.api.domain.report.service.sale.SaleReportService;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor

public class ReportServiceImpl implements ReportService {

    private static final String SALES_REPORT_TEMPLATE = "reports/ReporteVenta.jrxml";
    private static final String KARDEX_REPORT_TEMPLATE = "reports/ReporteKardex.jrxml";
    private static final String PURCHASES_REPORT_TEMPLATE = "reports/ReporteCompra.jrxml";
    private static final String INVENTORY_REPORT_TEMPLATE = "reports/ReporteInventario.jrxml";
    private static final String LOGO_PATH = "reports/logo.png";
    private static final int MIN_REPORT_YEAR = 2000;
    private static final DateTimeFormatter PERIOD_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final SaleReportService saleReportService;
    private final KardexReportService kardexReportService;
    private final PurchaseReportService purchaseReportService;
    private final InventoryReportService inventoryReportService;
    private final JasperPdfGenerator pdfGenerator;

    @Override
    public byte[] generatePdfSalesReport(int year) {
        validateYear(year);
        return generatePdfSalesReport(ReportFrequency.ANNUAL, LocalDate.of(year, 1, 1));
    }

    @Override
    public byte[] generatePdfSalesReport(ReportFrequency frequency, LocalDate referenceDate) {

        ReportPeriod period = frequency.resolve(referenceDate);

        List<MonthlySalesDTO> salesAnalysis = saleReportService.getSalesAnalysis(frequency, period);
        SaleRanking rankings = saleReportService.getSaleRankings(period);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("logoEmpresa", loadLogo());
        parameters.put("periodo", period.label());

        parameters.put("dsVentasMensuales", new JRBeanCollectionDataSource(salesAnalysis));
        parameters.put("dsTopIngresos", new JRBeanCollectionDataSource(rankings.topRevenue()));
        parameters.put("dsBottomIngresos", new JRBeanCollectionDataSource(rankings.bottomRevenue()));
        parameters.put("dsTopVendidos", new JRBeanCollectionDataSource(rankings.topSelling()));
        parameters.put("dsBottomVendidos", new JRBeanCollectionDataSource(rankings.bottomSelling()));

        parameters.put("dsGraficoVentasMensuales", new JRBeanCollectionDataSource(salesAnalysis));
        parameters.put("dsGraficoTopIngresos", new JRBeanCollectionDataSource(rankings.topRevenue()));
        parameters.put("dsGraficoBottomIngresos", new JRBeanCollectionDataSource(rankings.bottomRevenue()));
        parameters.put("dsGraficoTopVendidos", new JRBeanCollectionDataSource(rankings.topSelling()));
        parameters.put("dsGraficoBottomVendidos", new JRBeanCollectionDataSource(rankings.bottomSelling()));

        return pdfGenerator.generate(SALES_REPORT_TEMPLATE, parameters);
    }

    @Override
    public byte[] generatePdfKardexReport(
            String productCode,
            LocalDateTime startDate,
            LocalDateTime endDate
    ) {
        validateDateRange(startDate, endDate);
        List<KardexItemDTO> kardex = kardexReportService.generateKardex(productCode, startDate, endDate);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("logoEmpresa", loadLogo());
        parameters.put("periodo",
                startDate.format(PERIOD_FORMAT) + " - " + endDate.format(PERIOD_FORMAT));
        return pdfGenerator.generate(
                KARDEX_REPORT_TEMPLATE,
                parameters,
                new JRBeanCollectionDataSource(kardex)
        );
    }

    @Override
    public byte[] generatePdfPurchasesReport(ReportFrequency frequency, LocalDate referenceDate) {

        ReportPeriod period = frequency.resolve(referenceDate);
        PurchaseReportData data = purchaseReportService.getPurchaseReportData(frequency, period);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("logoEmpresa", loadLogo());
        parameters.put("periodo", period.label());
        parameters.put("kpiTotalInvertido", data.totalInvested());
        parameters.put("kpiNumeroCompras", data.purchaseCount());
        parameters.put("kpiTotalUnidades", data.totalUnits());
        parameters.put("kpiProveedorPrincipal", data.mainSupplier());
        parameters.put("dsEvolucionCompras", new JRBeanCollectionDataSource(data.evolution()));
        parameters.put("dsTopProveedores", new JRBeanCollectionDataSource(data.topSuppliers()));
        parameters.put("dsGraficoTopProveedores", new JRBeanCollectionDataSource(data.topSuppliers()));
        parameters.put("dsTopProductos", new JRBeanCollectionDataSource(data.topProducts()));
        parameters.put("dsGraficoTopProductos", new JRBeanCollectionDataSource(data.topProducts()));
        parameters.put("dsDistribucionGasto", new JRBeanCollectionDataSource(data.categorySpending()));
        return pdfGenerator.generate(PURCHASES_REPORT_TEMPLATE, parameters);
    }

    @Override
    public byte[] generatePdfInventoryReport(ReportFrequency frequency, LocalDate referenceDate) {

        ReportPeriod rotationPeriod = frequency != null
                ? frequency.resolve(referenceDate)
                : new ReportPeriod(
                LocalDateTime.now().minusDays(90),
                LocalDateTime.now(),
                "ÚLTIMOS 90 DÍAS");

        InventoryReportData data = inventoryReportService.getInventoryReportData(rotationPeriod);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("logoEmpresa", loadLogo());
        parameters.put("ventanaRotacion", rotationPeriod.label());
        parameters.put("kpiTotalProductos", data.totalProducts());
        parameters.put("kpiUnidadesStock", data.totalUnits());
        parameters.put("kpiValorInventario", data.totalValue());
        parameters.put("kpiAlertasStock", data.lowStockCount() + " / " + data.outOfStockCount());
        parameters.put("dsValorPorCategoria", new JRBeanCollectionDataSource(data.categoryValues()));
        parameters.put("dsTendenciaInventario", new JRBeanCollectionDataSource(data.trend()));
        parameters.put("dsMayorRotacion", new JRBeanCollectionDataSource(data.topRotation()));
        parameters.put("dsMenorRotacion", new JRBeanCollectionDataSource(data.bottomRotation()));
        return pdfGenerator.generate(INVENTORY_REPORT_TEMPLATE, parameters);
    }

    private void validateYear(int year) {
        if (year < MIN_REPORT_YEAR || year > LocalDate.now().getYear()) {
            throw new InvalidReportYearException(year);
        }
    }

    private void validateDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        if (!startDate.isBefore(endDate)) {
            throw new InvalidReportDateRangeException(startDate, endDate);
        }
    }

    private InputStream loadLogo() {
        try {
            return new ClassPathResource(LOGO_PATH).getInputStream();
        } catch (IOException e) {
            throw new ReportGenerationException(LOGO_PATH, e);
        }
    }
}