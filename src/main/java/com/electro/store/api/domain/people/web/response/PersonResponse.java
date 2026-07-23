package com.electro.store.api.domain.people.web.response;

public record PersonResponse(
        String firstName,
        String lastName,
        String nationalId,
        String phone
) {
}
