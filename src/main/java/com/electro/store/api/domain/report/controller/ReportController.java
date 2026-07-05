package com.electro.store.api.domain.report.controller;

import com.electro.store.api.domain.report.service.ReportService;
import com.electro.store.api.domain.report.service.saleReportService.SaleReportService;
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
    public ResponseEntity<byte[]> reporteVenta(@PathVariable int year) {
        try{
            byte[] pdf = reportService.generatePdfSalesReport(year);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=Reporte_Ventas_" + year + ".pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdf);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }


}
