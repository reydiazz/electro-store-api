package com.electro.store.api.domain.sales.web.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record CreateSaleRequest (
        @NotBlank(message = "Customer code is required")
        String customerCode,

        @Valid
        @NotEmpty(message = "Sale details are requiered")
        List<CreateSaleDetailRequest> detail
) {
}
