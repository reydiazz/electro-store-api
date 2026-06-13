package com.electro.store.api.domain.sales.repository;

import com.electro.store.api.domain.sales.model.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale, String> {

}
