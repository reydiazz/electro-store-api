package com.electro.store.api.domain.product.web.request;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record CreateProductRequest(

        @NotBlank(message = "Product category code is required")
        String categoryCode,

        @NotBlank(message = "Product name is required")
        String name,
        String brand,
        String model,

        @NotBlank(message = "Product sale price is required")
        BigDecimal salePrice,
        String description,
        Integer warrantyMonths
) {
}
