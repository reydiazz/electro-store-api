package com.electro.store.api.domain.buys.repository;

import com.electro.store.api.domain.buys.model.entity.Suppliers;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Suppliers,String> {
}
