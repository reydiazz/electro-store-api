package com.electro.store.api.domain.product.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "product_categories")
@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductCategory {

    @Id
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    public ProductCategory(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public void update(String name) {
        this.name = name;
    }

}
