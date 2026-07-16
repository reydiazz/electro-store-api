package com.electro.store.api.domain.report.service.inventory;

import com.electro.store.api.domain.report.model.ReportPeriod;

public interface InventoryReportService {

    InventoryReportData getInventoryReportData(ReportPeriod rotationPeriod);

}
