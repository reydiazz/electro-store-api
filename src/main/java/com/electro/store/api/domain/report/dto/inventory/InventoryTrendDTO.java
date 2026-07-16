package com.electro.store.api.domain.report.dto.inventory;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryTrendDTO {

    private String periodLabel;
    private Long totalUnits;
}
