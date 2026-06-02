package com.electro.store.api.domain.buys.web.request;

import jakarta.validation.constraints.NotBlank;

public record CreateSupplierRequest(
        @NotBlank(message = "Supplier tax id is required")
        String taxId,
        @NotBlank(message = "Supplier trade name is required")
        String tradeName,
        @NotBlank(message = "Supplier number phone is required")
        String phone,
        @NotBlank(message = "Supplier legal name is required")
        String legalName
) {
}
