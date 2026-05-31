package com.electro.store.api.domain.movement.component;

import com.electro.store.api.domain.movement.model.entity.GuideDetail;
import com.electro.store.api.domain.movement.web.response.GuideDetailResponse;
import com.electro.store.api.domain.product.component.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GuideDetailMapper {

    private final ProductMapper productMapper;

    public GuideDetailResponse toResponse(GuideDetail detail){
        return new GuideDetailResponse(
                detail.getCode(),
                productMapper.toResponse(detail.getProduct()),
                detail.getQuantity()
        );
    }
}
