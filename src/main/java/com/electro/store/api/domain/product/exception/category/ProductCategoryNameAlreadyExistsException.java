package com.electro.store.api.domain.product.exception.category;

import com.electro.store.api.domain.people.exception.person.PersonErrorCode;
import com.electro.store.api.domain.product.model.entity.ProductCategory;
import com.electro.store.api.shared.exception.BusinessException;

public class ProductCategoryNameAlreadyExistsException extends BusinessException {

    public ProductCategoryNameAlreadyExistsException(String name) {
        super("Category with name '%s' already exists".formatted(name), ProductCategoryErrorCode.PRODUCT_CATEGORY_ALREADY_EXISTS);
    }

}
