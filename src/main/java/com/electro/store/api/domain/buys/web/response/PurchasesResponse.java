package com.electro.store.api.domain.buys.web.response;

import com.electro.store.api.domain.auth.web.response.UserResponse;
import java.time.LocalDateTime;
import java.util.List;

public record PurchasesResponse(
        String code,
        UserResponse user,
        SupplierResponse supplier,
        LocalDateTime purchaseDate,
        List<PurchasesDetailsResponse> details
) {
}
