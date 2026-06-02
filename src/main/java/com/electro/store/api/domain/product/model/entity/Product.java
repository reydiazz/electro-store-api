package com.electro.store.api.domain.product.model.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_code", nullable = false)
    private ProductCategory category;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "model",  nullable = false)
    private String model;

    @Column(name = "sale_price", nullable = false)
    private BigDecimal salePrice;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "description")
    private String description;

    @Column(name = "warranty_months", nullable = false)
    private Integer warrantyMonths;

    public Product(String code, ProductCategory category, String name, String brand, String model, BigDecimal salePrice, String description, Integer warrantyMonths) {
        this.code = code;
        this.category = category;
        this.name = name;
        this.brand = brand;
        this.model = model;
        this.salePrice = salePrice;
        this.stock = 0;
        this.description = description;
        this.warrantyMonths = warrantyMonths;
    }

    public void update(ProductCategory category, String name, String brand, String model, BigDecimal salePrice, String description, Integer warrantyMonths) {
        this.category = category;
        this.name = name;
        this.brand = brand;
        this.model = model;
        this.salePrice = salePrice;
        this.description = description;
        this.warrantyMonths = warrantyMonths;
    }

    public void increaseStock(Integer quantity) {
        this.stock += quantity;
    }

    public void decreaseStock(Integer quantity) {
        this.stock -= quantity;
    }

    public boolean hasEnoughStock(Integer quantity) {
        return this.stock >= quantity;
    }

}
