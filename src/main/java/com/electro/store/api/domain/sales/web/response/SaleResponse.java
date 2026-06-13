package com.electro.store.api.domain.sales.web.response;

import com.electro.store.api.domain.auth.web.response.UserResponse;
import com.electro.store.api.domain.people.web.response.CustomerResponse;

import java.time.LocalDateTime;
import java.util.List;

public record SaleResponse(
        String code,
        UserResponse user,
        CustomerResponse customer,
        LocalDateTime saleDate,
        List<SaleDetailResponse> details
) {
}
