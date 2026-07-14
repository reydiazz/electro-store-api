package com.electro.store.api.domain.report.web.controller;

import com.electro.store.api.domain.report.model.enums.ReportFrequency;
import com.electro.store.api.domain.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/reports")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/sales-report/{year}")
    public ResponseEntity<byte[]> generateSalesReport(@PathVariable int year) {
        byte[] pdf = reportService.generatePdfSalesReport(year);
        return pdfResponse(pdf, "Reporte_Ventas_" + year);
    }

    @GetMapping("/kardex-report")
    public ResponseEntity<byte[]> generateKardexReport(
            @RequestParam(required = false) String productCode,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate
    ) {
        LocalDateTime effectiveStart = startDate != null
                ? startDate
                : LocalDate.now().withDayOfYear(1).atStartOfDay();
        LocalDateTime effectiveEnd = endDate != null
                ? endDate
                : LocalDateTime.now();
        byte[] pdf = reportService.generatePdfKardexReport(productCode, effectiveStart, effectiveEnd);
        String filename = productCode != null
                ? "Reporte_Kardex_" + productCode
                : "Reporte_Kardex_General";
        return pdfResponse(pdf, filename);
    }

    @GetMapping("/purchases-report")
    public ResponseEntity<byte[]> generatePurchasesReport(
            @RequestParam(required = false, defaultValue = "ANNUAL") ReportFrequency frequency,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        LocalDate referenceDate = date != null ? date : LocalDate.now();
        byte[] pdf = reportService.generatePdfPurchasesReport(frequency, referenceDate);
        return pdfResponse(pdf, "Reporte_Compras_" + frequency.name());
    }

    private ResponseEntity<byte[]> pdfResponse(byte[] pdf, String filename) {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + filename + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

}
