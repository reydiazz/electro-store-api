package com.electro.store.api.domain.buys.web.request;

import jakarta.validation.constraints.NotBlank;

public record UpdateSupplierRequest(
        String taxId,
        @NotBlank(message = " Supplier trade name is required")
        String tradeName,
        String phone,
        String legalName
) {
}
