package com.electro.store.api.domain.buys.model.entity;

import com.electro.store.api.domain.product.model.entity.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "purchase_details")
@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class PurchasesDetails {
    @Id
    @Column(name = "code",unique = true,nullable = false)
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_code", nullable = false)
    private Purchases purchase;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_code", nullable = false)
    private Product product;

    @Column(name = "purchase_price", nullable = false)
    private BigDecimal purchasePrice;

    @Column(name = "quantity",nullable = false)
    private Integer quantity;

    public  PurchasesDetails (String code,Purchases purchase,Product product,BigDecimal purchasePrice,Integer quantity){
        this.code= code;
        this.purchase = purchase;
        this.product = product;
        this.purchasePrice= purchasePrice;
        this.quantity = quantity;
    }
}
