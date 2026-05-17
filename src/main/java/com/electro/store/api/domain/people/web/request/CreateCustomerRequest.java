package com.electro.store.api.domain.people.web.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateCustomerRequest(
        @NotNull(message = "Customer data is required")
        CreatePersonRequest person,
        @NotBlank(message = "Customer tax id is required")
        String taxId
) {
}
