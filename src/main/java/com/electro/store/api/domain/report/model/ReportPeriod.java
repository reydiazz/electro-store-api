package com.electro.store.api.domain.report.model;

import java.time.LocalDateTime;

public record ReportPeriod (LocalDateTime start, LocalDateTime endExclusive, String label) {
}
