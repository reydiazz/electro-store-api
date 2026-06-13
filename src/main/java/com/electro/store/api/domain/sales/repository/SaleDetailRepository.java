package com.electro.store.api.domain.sales.repository;

import com.electro.store.api.domain.sales.model.entity.SaleDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleDetailRepository extends JpaRepository<SaleDetail, String> {
}
