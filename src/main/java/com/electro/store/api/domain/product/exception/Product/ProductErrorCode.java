package com.electro.store.api.domain.product.exception.Product;

import com.electro.store.api.shared.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum ProductErrorCode implements ErrorCode {
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND);

    private final HttpStatus httpStatus;
    ProductErrorCode(HttpStatus httpStatus){
        this.httpStatus = httpStatus;
    }

    @Override
    public String getCode(){
        return name();
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
