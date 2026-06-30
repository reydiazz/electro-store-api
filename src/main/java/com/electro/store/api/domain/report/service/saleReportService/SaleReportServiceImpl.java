package com.electro.store.api.domain.report.service.saleReportService;

import com.electro.store.api.domain.report.service.ReportService;
import com.electro.store.api.domain.sales.model.entity.Sale;
import com.electro.store.api.domain.sales.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor

public class SaleReportServiceImpl implements SaleReportService{

    private final SaleRepository saleRepository;
    private final ReportService reportService;

    LocalDate start = LocalDate.of(2026,1,1);
    LocalDate end = LocalDate.of(2026,12,31);
    List<Sale> sales = saleRepository.findBySaleDateBetween(start.atStartOfDay(), end.atTime(23,59,59));

}
