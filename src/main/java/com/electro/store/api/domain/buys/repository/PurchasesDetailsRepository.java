package com.electro.store.api.domain.buys.repository;

import com.electro.store.api.domain.buys.model.entity.PurchasesDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchasesDetailsRepository  extends JpaRepository<PurchasesDetails,String> {
}
