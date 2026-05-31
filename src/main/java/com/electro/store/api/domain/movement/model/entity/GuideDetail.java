package com.electro.store.api.domain.movement.model.entity;

import com.electro.store.api.domain.product.model.entity.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "guide_details")
@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class GuideDetail {

    @Id
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guide_code", nullable = false)
    private InventoryGuide guide;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_code", nullable = false)
    private Product  product;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    public GuideDetail(
            String code,
            InventoryGuide guide,
            Product product,
            Integer quantity
    ) {
        this.code = code;
        this.guide = guide;
        this.product = product;
        this.quantity = quantity;
    }
}
