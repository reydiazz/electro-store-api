package com.electro.store.api.domain.product.web.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record UpdateProductRequest(
        @NotBlank(message = "Product category code is required")
        String categoryCode,
        @NotBlank(message = "Product name is required")
        String name,
        @NotBlank(message = "Brand name is required")
        String brand,
        @NotBlank(message = "Model name is required")
        String model,
        @NotNull(message = "Product sale price is required")
        @DecimalMin(value = "0.01", message = "Product sale price must be greater than zero")
        BigDecimal salePrice,
        String description,
        @NotNull(message = "Warranty months is required")
        @PositiveOrZero(message = "Warranty months must be zero or a positive number")
        Integer warrantyMonths,
        @NotNull(message = "Low stock  is required")
        Integer lowStock
) {
}
