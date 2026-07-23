package com.electro.store.api.domain.buys.web.response;


import com.electro.store.api.domain.product.web.response.ProductResponse;
import java.math.BigDecimal;


public record PurchasesDetailsResponse(
        String code,
        ProductResponse product,
        BigDecimal purchasePrice,
        Integer quantity
) {

}
