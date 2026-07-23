package com.electro.store.api.domain.movement.component;

import com.electro.store.api.domain.auth.component.UserMapper;
import com.electro.store.api.domain.movement.model.entity.InventoryGuide;
import com.electro.store.api.domain.movement.web.response.InventoryGuideResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InventoryGuideMapper {

    private final UserMapper userMapper;
    private final GuideDetailMapper guideDetailMapper;

    public InventoryGuideResponse toResponse(InventoryGuide guide){
        return new InventoryGuideResponse(
                guide.getCode(),
                userMapper.toResponse(guide.getUser()),
                guide.getType(),
                guide.getReason(),
                guide.getDescription(),
                guide.getGuideDate(),
                guide.getDetails()
                        .stream()
                        .map(guideDetailMapper::toResponse)
                        .toList()
        );
    }
}
