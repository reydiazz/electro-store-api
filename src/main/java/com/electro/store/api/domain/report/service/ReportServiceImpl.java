package com.electro.store.api.domain.report.service;

import com.electro.store.api.domain.report.component.JasperPdfGenerator;
import com.electro.store.api.domain.report.dto.sale.MonthlySalesDTO;
import com.electro.store.api.domain.report.dto.sale.RankingRevenueDTO;
import com.electro.store.api.domain.report.dto.sale.RankingSellingDTO;
import com.electro.store.api.domain.report.exception.InvalidReportYearException;
import com.electro.store.api.domain.report.exception.ReportGenerationException;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class ReportServiceImpl implements ReportService {

    private static final String SALES_REPORT_TEMPLATE = "reports/ReporteVenta.jrxml";
    private static final String LOGO_PATH = "reports/logo.png";
    private static final int MIN_REPORT_YEAR = 2000;

    private final SaleReportService saleReportService;
    private final JasperPdfGenerator pdfGenerator;

    @Override
    public byte[] generatePdfSalesReport(int year) {

        validateYear(year);

        List<MonthlySalesDTO> monthlySales = saleReportService.getMonthlySales(year);
        SaleRanking rankings = saleReportService.getSaleRankings(year);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("logoEmpresa", loadLogo());

        parameters.put("dsVentasMensuales", new JRBeanCollectionDataSource(monthlySales));
        parameters.put("dsTopIngresos", new JRBeanCollectionDataSource(rankings.topRevenue()));
        parameters.put("dsBottomIngresos", new JRBeanCollectionDataSource(rankings.bottomRevenue()));
        parameters.put("dsTopVendidos", new JRBeanCollectionDataSource(rankings.topSelling()));
        parameters.put("dsBottomVendidos", new JRBeanCollectionDataSource(rankings.bottomSelling()));

        parameters.put("dsGraficoVentasMensuales", new JRBeanCollectionDataSource(monthlySales));
        parameters.put("dsGraficoTopIngresos", new JRBeanCollectionDataSource(rankings.topRevenue()));
        parameters.put("dsGraficoBottomIngresos", new JRBeanCollectionDataSource(rankings.bottomRevenue()));
        parameters.put("dsGraficoTopVendidos", new JRBeanCollectionDataSource(rankings.topSelling()));
        parameters.put("dsGraficoBottomVendidos", new JRBeanCollectionDataSource(rankings.bottomSelling()));
        parameters.put("anio", year);

        return pdfGenerator.generate(SALES_REPORT_TEMPLATE, parameters);
    }

    private void validateYear(int year) {
        if (year < MIN_REPORT_YEAR || year > LocalDate.now().getYear()) {
            throw new InvalidReportYearException(year);
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
