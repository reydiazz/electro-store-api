package com.electro.store.api.domain.people.web.request;

import jakarta.validation.constraints.NotNull;

public record UpdateCustomerRequest(
        @NotNull(message = "Customer data is required")
        UpdatePersonRequest person,
        String taxId
) {
}
