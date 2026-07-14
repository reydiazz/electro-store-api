package com.electro.store.api.domain.report.service.purchase;

import com.electro.store.api.domain.report.model.ReportPeriod;
import com.electro.store.api.domain.report.model.enums.ReportFrequency;

public interface PurchaseReportService {
    PurchaseReportData getPurchaseReportData(ReportFrequency frequency, ReportPeriod period);
}
