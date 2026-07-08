package com.electro.store.api.domain.report.dto.sale;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class RankingSellingDTO {

    private String nameProduct;
    private String nameCategoryProduct;
    private Integer quantity;

}
