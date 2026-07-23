package com.electro.store.api.domain.buys.model.entity;

import com.electro.store.api.domain.auth.model.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "purchases")
@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class Purchases {

    @Id
    @Column (name = "code",nullable = false,unique = true)
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_code", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_code", nullable = false)
    private Supplier supplier;

    @Column(name = "purchase_date",nullable = false)
    private LocalDateTime purchaseDate;

    @OneToMany(mappedBy = "purchase", fetch = FetchType.LAZY)
    private List<PurchasesDetails> details = new ArrayList<>();;

    public  Purchases (String code ,User user ,Supplier suppliers ,LocalDateTime purchaseDate){
        this.code= code;
        this.user= user;
        this.supplier = suppliers;
        this.purchaseDate=purchaseDate;
    }

    public void addDetail(PurchasesDetails details){
        this.details.add(details);
    }

}