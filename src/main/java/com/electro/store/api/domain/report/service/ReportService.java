package com.electro.store.api.domain.report.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface ReportService {

    byte[] generatePdfSalesReport(int year);

    byte[] generatePdfKardexReport(
            String productCode,
            LocalDateTime startDate,
            LocalDateTime endDate
    ) ;



}
