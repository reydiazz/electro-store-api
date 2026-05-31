package com.electro.store.api.domain.movement.exception.InventoryGuide;

import com.electro.store.api.shared.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum InventoryGuideErrorCode implements ErrorCode {

    INVENTORY_GUIDE_NOT_FOUND(HttpStatus.NOT_FOUND);

    private final HttpStatus httpStatus;

    InventoryGuideErrorCode(HttpStatus httpStatus){
        this.httpStatus = httpStatus;
    }

    @Override
    public String getCode(){
        return name();
    }

    @Override
    public HttpStatus getHttpStatus(){
        return httpStatus;
    }
}
