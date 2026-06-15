package com.electro.store.api.domain.buys.repository;

import com.electro.store.api.domain.buys.model.entity.Purchases;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchasesRepository extends JpaRepository<Purchases,String> {
}
