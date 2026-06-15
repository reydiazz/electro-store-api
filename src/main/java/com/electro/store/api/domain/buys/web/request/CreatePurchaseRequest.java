package com.electro.store.api.domain.buys.web.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record CreatePurchaseRequest(
        @NotBlank(message = "Supplier is required")
        String supplierCode,

        @Valid
        @NotEmpty(message = "Purchase details are required")
        List<CreatePurchaseDetailRequest> detail
) {
}
