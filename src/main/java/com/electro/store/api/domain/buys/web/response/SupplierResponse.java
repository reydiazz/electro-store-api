package com.electro.store.api.domain.buys.web.response;

public record SupplierResponse(
        String code,
        String taxId,
        String tradeName,
        String phone,
        String legalName
) {
}
