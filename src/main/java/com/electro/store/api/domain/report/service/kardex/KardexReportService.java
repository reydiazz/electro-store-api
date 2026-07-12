package com.electro.store.api.domain.report.service.kardex;

import com.electro.store.api.domain.report.dto.kardex.KardexItemDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface KardexReportService {

    Integer calculateInitialBalance(
            String productCode,
            LocalDateTime startDate
    );

    List<KardexItemDTO> generateKardex(
            String productCode,
            LocalDateTime startDate,
            LocalDateTime endDate
    );

}
