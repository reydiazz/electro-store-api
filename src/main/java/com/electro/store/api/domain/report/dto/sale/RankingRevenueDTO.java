package com.electro.store.api.domain.report.dto.sale;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class RankingRevenueDTO {

    private String nameProduct;
    private String nameCategoryProduct;
    private BigDecimal saleByTotalProduct;

}
