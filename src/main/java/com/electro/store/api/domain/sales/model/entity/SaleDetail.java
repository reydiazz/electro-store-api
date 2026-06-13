package com.electro.store.api.domain.sales.model.entity;

import com.electro.store.api.domain.product.model.entity.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "sale_details")
@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class SaleDetail {
    @Id
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_code", nullable = false)
    private Sale sale;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_code", nullable = false)
    private Product product;

    @Column(name = "sale_price", nullable = false)
    private BigDecimal salePrice;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    public SaleDetail(
            String code,
            Sale sale,
            Product product,
            BigDecimal salePrice,
            Integer quantity
    ) {
        this.code = code;
        this.sale = sale;
        this.product = product;
        this.salePrice = salePrice;
        this.quantity = quantity;
    }
}
