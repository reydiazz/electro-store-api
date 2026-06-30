package com.electro.store.api.domain.report.controller;

import com.electro.store.api.domain.people.model.entity.Employee;
import com.electro.store.api.domain.people.service.EmployeeService;
import com.electro.store.api.domain.product.service.ProductService;
import com.electro.store.api.domain.product.web.response.ProductResponse;
import com.electro.store.api.domain.report.service.ReportService;
import com.electro.store.api.domain.sales.service.SaleService;
import com.electro.store.api.domain.sales.web.response.SaleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reportes")
@RequiredArgsConstructor

public class ReportController {

    private final ReportService reportService;
    private final EmployeeService employeeService;
    private final ProductService productService;
    private final SaleService saleService;

    @GetMapping("/sales-report")
    public ResponseEntity<byte[]> reporteVenta() {
        try {
            page<>
        }
    }
    /*
    @GetMapping("/empleados")
    public ResponseEntity<byte[]> reporteEmpleados() {
        try {
            Page<Employee> employeePage = employeeService.findAll(Pageable.unpaged());
            List<EmployeeReportDTO> empleados = employeePage.getContent()
                    .stream()
                    .map(emp -> new EmployeeReportDTO(
                            emp.getCode(),
                            emp.getPerson().getFirstName(),
                            emp.getPerson().getLastName(),
                            emp.getPerson().getPhone(),
                            emp.getPerson().getNationalId(),
                            emp.getPosition().name(),
                            emp.getSalary()
                    ))
                    .toList();
            Map<String, Object> parametros = new HashMap<>();
            InputStream logo = new ClassPathResource("reports/logo.png").getInputStream();
            parametros.put("logoEmpresa", logo);

            byte[] pdf = reportService.generarReporte(
                    "ReporteEmpleado",
                    empleados,
                    parametros
            );

            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=empleados.pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdf);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/inventario")
    public ResponseEntity<byte[]> reporteInventario() {
        try {
            Page<ProductResponse> productPage = productService.findAll(Pageable.unpaged());
            List<InventoryReportDTO> productos = productPage.getContent()
                    .stream()
                    .map(p -> new InventoryReportDTO(
                            p.code(),
                            p.name(),
                            p.brand(),
                            p.salePrice(),
                            p.stock()
                    ))
                    .toList();
            Map<String, Object> parametros = new HashMap<>();
            InputStream logo = new ClassPathResource("reports/logo.png").getInputStream();
            parametros.put("logoEmpresa", logo);

            byte[] pdf = reportService.generarReporte(
                    "ReporteInventario",
                    productos,
                    parametros
            );

            return ResponseEntity.ok().header(
                    HttpHeaders.CONTENT_DISPOSITION,
                    "inline; filename=inventario.pdf"
            ).contentType(MediaType.APPLICATION_PDF).body(pdf);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/producto")
    public ResponseEntity<byte[]> reporteProducto() {
        try {
            Page<ProductResponse> productPage = productService.findAll(Pageable.unpaged());
            List<ProductReportDTO> productos = productPage.getContent().stream().map(pr -> new ProductReportDTO(
                    pr.code(),
                    pr.name(),
                    pr.brand(),
                    pr.model(),
                    pr.categoryName(),
                    pr.salePrice(),
                    pr.warrantyMonths()
            )).toList();
            Map<String, Object> parametros = new HashMap<>();
            InputStream logo = new ClassPathResource("reports/logo.png").getInputStream();
            parametros.put("logoEmpresa", logo);

            byte[] pdf = reportService.generarReporte(
                    "ReporteProducto",
                    productos,
                    parametros
            );

            return ResponseEntity.ok().header(
                    HttpHeaders.CONTENT_DISPOSITION,
                    "inline; filename=productos.pdf").contentType(MediaType.APPLICATION_PDF).body(pdf);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/venta")
    public ResponseEntity<byte[]> reporteVenta() {
        try {
            Page<SaleResponse> salePage = saleService.findAll(Pageable.unpaged());
            List<SaleReportDTO> sales = salePage.getContent().stream().map( v -> new SaleReportDTO(
                    v.code(),
                    v.saleDate(),
                    v.customer().person().firstName(),
                    v.user().firstName()
            )).toList();
            Map<String, Object> parametros = new HashMap<>();
            InputStream logo = new ClassPathResource("reports/logo.png").getInputStream();
            parametros.put("logoEmpresa", logo);

            byte[] pdf = reportService.generarReporte(
                    "ReporteVenta",
                    sales,
                    parametros
            );

            return ResponseEntity.ok().header(
                    HttpHeaders.CONTENT_DISPOSITION,
                    "inline; filename=productos.pdf").contentType(MediaType.APPLICATION_PDF).body(pdf);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }*/

}
