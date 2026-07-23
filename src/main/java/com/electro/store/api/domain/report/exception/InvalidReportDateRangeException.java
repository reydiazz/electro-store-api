package com.electro.store.api.domain.report.exception;

import com.electro.store.api.shared.exception.BusinessException;

import java.time.LocalDateTime;

public class InvalidReportDateRangeException extends BusinessException {

    public InvalidReportDateRangeException(LocalDateTime startDate, LocalDateTime endDate) {
        super(
                "Invalid report date range: start '%s' must be before end '%s'"
                        .formatted(startDate, endDate),
                ReportErrorCode.REPORT_INVALID_DATE_RANGE
        );
    }
}
