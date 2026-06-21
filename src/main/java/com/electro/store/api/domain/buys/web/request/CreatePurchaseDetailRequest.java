package com.electro.store.api.domain.buys.web.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record CreatePurchaseDetailRequest (
        @NotBlank(message = "Product code is required")
        String productCode,

        @NotNull(message = "Purchase price is required")
        @DecimalMin(value = "0.0", inclusive = false, message = "Purchase price must be greater than zero")
        BigDecimal purchasePrice,

        @NotNull(message = "Quantity is required")
        @Min( value= 1 , message = "Quantity must be greater than zero")
        Integer quantity
) {

}
