package com.electro.store.api.domain.people.web.request;

import jakarta.validation.constraints.NotBlank;

public record CreatePersonRequest(
        @NotBlank(message = "Person first name is required")
        String firstName,
        @NotBlank(message = "Person last name is required")
        String lastName,
        String nationalId,
        String phone
) {
}
