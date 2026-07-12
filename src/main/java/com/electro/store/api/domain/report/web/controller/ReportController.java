package com.electro.store.api.domain.report.web.controller;

import com.electro.store.api.domain.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;


@RestController
@RequestMapping("/api/reports")
@PreAuthorize("hasAnyRole('ADMIN')")
@RequiredArgsConstructor

public class ReportController {

    private final ReportService reportService;

    @GetMapping("/sales-report/{year}")
    public ResponseEntity<byte[]> generateSalesReport(@PathVariable int year) {

        byte[] pdf = reportService.generatePdfSalesReport(year);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=Reporte_Ventas_" + year + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    @GetMapping("/kardex-report")
    public ResponseEntity<byte[]> generateKardexReport(

            @RequestParam String productCode,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime startDate,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime endDate

    ) {

        byte[] pdf = reportService.generatePdfKardexReport(
                productCode,
                startDate,
                endDate
        );

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=Reporte_Kardex_" + productCode + ".pdf"
                )
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

}
