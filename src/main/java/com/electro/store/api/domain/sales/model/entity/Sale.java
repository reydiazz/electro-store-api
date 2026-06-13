package com.electro.store.api.domain.sales.model.entity;

import com.electro.store.api.domain.auth.model.entity.User;
import com.electro.store.api.domain.people.model.entity.Customer;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "sales")
@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class Sale {
    @Id
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_code", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_code", nullable = false)
    private Customer customer;

    @Column(name = "sale_date", nullable = false)
    private LocalDateTime saleDate;

    @OneToMany(mappedBy = "sale", fetch = FetchType.LAZY)
    private List<SaleDetail> details = new ArrayList<>();

    public Sale(
            String code,
            User user,
            Customer customer,
            LocalDateTime saleDate
    ) {
        this.code = code;
        this.user = user;
        this.customer = customer;
        this.saleDate = saleDate;
    }

    public void addDetail(SaleDetail detail) {
        this.details.add(detail);
    }
}
