package com.electro.store.api.domain.sales.component;

import com.electro.store.api.domain.product.component.ProductMapper;
import com.electro.store.api.domain.sales.model.entity.SaleDetail;
import com.electro.store.api.domain.sales.web.response.SaleDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SaleDetailMapper {

    private final ProductMapper productMapper;

    public SaleDetailResponse toResponse(SaleDetail detail) {

        return new SaleDetailResponse(
                detail.getCode(),
                productMapper.toResponse(detail.getProduct()),
                detail.getSalePrice(),
                detail.getQuantity()
        );
    }
}
