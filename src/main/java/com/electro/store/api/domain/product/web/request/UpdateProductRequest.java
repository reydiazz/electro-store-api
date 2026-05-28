package com.electro.store.api.domain.product.web.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record UpdateProductRequest(
        @NotBlank(message = "Product category code is required")
        String categoryCode,

        @NotBlank(message = "Product name is required")
        String name,
        String brand,
        String model,

        @NotNull(message = "Product sale price is required")
        @DecimalMin(value = "0.01", message = "Product sale price must be greater than zero")
        BigDecimal salePrice,
        String description,
        Integer warrantyMonths
) {
}
