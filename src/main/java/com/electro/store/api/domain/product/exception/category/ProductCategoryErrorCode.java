package com.electro.store.api.domain.product.exception.category;

import com.electro.store.api.shared.exception.ErrorCode;
import org.springframework.http.HttpStatus;

public enum ProductCategoryErrorCode implements ErrorCode {

    PRODUCT_CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND);

    private final HttpStatus httpStatus;

    ProductCategoryErrorCode(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    @Override
    public String getCode() {
        return name();
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

}
