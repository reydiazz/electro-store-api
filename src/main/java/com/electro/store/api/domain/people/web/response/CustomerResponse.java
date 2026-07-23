package com.electro.store.api.domain.people.web.response;

public record CustomerResponse(
        String code,
        PersonResponse person,
        String taxId
) {
}
