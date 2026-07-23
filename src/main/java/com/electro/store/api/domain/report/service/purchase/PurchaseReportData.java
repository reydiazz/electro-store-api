package com.electro.store.api.domain.report.service.purchase;

import com.electro.store.api.domain.report.dto.purchase.CategorySpendingDTO;
import com.electro.store.api.domain.report.dto.purchase.PurchaseEvolutionDTO;
import com.electro.store.api.domain.report.dto.purchase.TopPurchasedProductDTO;
import com.electro.store.api.domain.report.dto.purchase.TopSupplierDTO;

import java.math.BigDecimal;
import java.util.List;

public record PurchaseReportData(
        BigDecimal totalInvested,
        long purchaseCount,
        long totalUnits,
        String mainSupplier,
        List<PurchaseEvolutionDTO> evolution,
        List<TopSupplierDTO> topSuppliers,
        List<TopPurchasedProductDTO> topProducts,
        List<CategorySpendingDTO> categorySpending
) {
}
