package com.electro.store.api.domain.report.exception;

import com.electro.store.api.shared.exception.BusinessException;

public class InvalidReportYearException extends BusinessException {

    public InvalidReportYearException(int year) {
        super(
                "El año '%d' no es válido para generar el reporte".formatted(year),
                ReportErrorCode.REPORT_INVALID_YEAR
        );
    }
}
