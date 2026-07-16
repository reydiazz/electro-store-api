package com.electro.store.api.domain.report.dto.inventory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryCategoryValueDTO {

    private String categoryName;
    private BigDecimal totalValue;
}
