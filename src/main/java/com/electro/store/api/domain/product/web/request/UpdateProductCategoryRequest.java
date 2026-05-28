package com.electro.store.api.domain.product.web.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateProductCategoryRequest(
        @NotBlank(message = "Product category name is required")
        String name
) {
}
