package com.electro.store.api.domain.report.service.inventory;

import com.electro.store.api.domain.report.dto.inventory.InventoryCategoryValueDTO;
import com.electro.store.api.domain.report.dto.inventory.InventoryTrendDTO;
import com.electro.store.api.domain.report.dto.inventory.ProductRotationDTO;

import java.math.BigDecimal;
import java.util.List;

public record InventoryReportData(
        long totalProducts,
        long totalUnits,
        BigDecimal totalValue,
        long lowStockCount,
        long outOfStockCount,
        List<InventoryCategoryValueDTO> categoryValues,
        List<InventoryTrendDTO> trend,
        List<ProductRotationDTO> topRotation,
        List<ProductRotationDTO> bottomRotation
) {
}
