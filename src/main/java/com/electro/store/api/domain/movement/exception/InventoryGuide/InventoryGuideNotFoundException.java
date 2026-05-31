package com.electro.store.api.domain.movement.exception.InventoryGuide;

import com.electro.store.api.shared.exception.BusinessException;

public class InventoryGuideNotFoundException extends BusinessException {

    public InventoryGuideNotFoundException(String code){
        super(
                "Inventory guide with code " + code + " no found",
                InventoryGuideErrorCode.INVENTORY_GUIDE_NOT_FOUND
        );
    }
}
