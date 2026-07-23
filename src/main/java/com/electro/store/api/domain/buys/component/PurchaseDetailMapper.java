package com.electro.store.api.domain.buys.component;


import com.electro.store.api.domain.buys.model.entity.PurchasesDetails;
import com.electro.store.api.domain.buys.web.response.PurchasesDetailsResponse;
import com.electro.store.api.domain.product.component.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PurchaseDetailMapper {
    private final ProductMapper productMapper;

    public PurchasesDetailsResponse toResponse(PurchasesDetails detail) {
        return new PurchasesDetailsResponse(
                detail.getCode(),
                productMapper.toResponse(detail.getProduct()),
                detail.getPurchasePrice(),
                detail.getQuantity()
        );
    }
}
