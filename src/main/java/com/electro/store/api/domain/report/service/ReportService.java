package com.electro.store.api.domain.report.service;

import com.electro.store.api.domain.report.model.enums.ReportFrequency;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface ReportService {

    byte[] generatePdfSalesReport(int year);

    byte[] generatePdfKardexReport(
            String productCode,
            LocalDateTime startDate,
            LocalDateTime endDate
    ) ;

    byte[] generatePdfPurchasesReport(ReportFrequency frequency, LocalDate referenceDate);

}
