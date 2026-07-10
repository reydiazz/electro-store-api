package com.electro.store.api.domain.report.exception;

import com.electro.store.api.shared.exception.BusinessException;

public class ReportGenerationException extends BusinessException {

    public ReportGenerationException(String templatePath, Throwable cause) {
        super(
                "No se pudo generar el reporte a partir de la plantilla '%s'".formatted(templatePath),
                ReportErrorCode.REPORT_GENERATION_FAILED,
                cause
        );
    }
}
