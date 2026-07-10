package com.electro.store.api.domain.report.web.controller;

import com.electro.store.api.domain.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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


}
