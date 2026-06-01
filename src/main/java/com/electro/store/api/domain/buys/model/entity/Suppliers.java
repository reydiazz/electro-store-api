package com.electro.store.api.domain.buys.model.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "suppliers")
@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class Suppliers {

    @Id
    @Column(name = "code", unique = true, nullable = false)
    private String code;

    @Column(name = "tax_id")
    private String taxId;

    @Column(name = "trade_name", nullable = false)
    private String tradeName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "legal_name")
    private String legalName;

    public Suppliers(String code, String taxId, String tradeName, String phone, String legalName) {
        this.code = code;
        this.taxId = taxId;
        this.tradeName = tradeName;
        this.phone = phone;
        this.legalName = legalName;
    }

    public void update(String taxId, String tradeName, String phone, String legalName) {
        this.taxId = taxId;
        this.tradeName = tradeName;
        this.phone = phone;
        this.legalName = legalName;
    }
}
