package com.electro.store.api.domain.report.service.kardex;

import java.time.LocalDateTime;

public interface KardexReportService {

    Integer calculateInitialBalance(
            String productCode,
            LocalDateTime startDate
    );

}
