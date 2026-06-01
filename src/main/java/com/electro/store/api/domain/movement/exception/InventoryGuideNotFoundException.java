package com.electro.store.api.domain.movement.exception;

import com.electro.store.api.shared.exception.BusinessException;

public class InventoryGuideNotFoundException extends BusinessException {

    public InventoryGuideNotFoundException(String code) {
        super("Inventory guide with code '%s' not found".formatted(code), InventoryGuideErrorCode.INVENTORY_GUIDE_NOT_FOUND);
    }

}
