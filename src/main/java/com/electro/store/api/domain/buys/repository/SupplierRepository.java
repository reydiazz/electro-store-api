package com.electro.store.api.domain.buys.repository;

import com.electro.store.api.domain.buys.model.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplierRepository extends JpaRepository<Supplier,String> {

    boolean existsByTaxId(String taxId);

    boolean existsByTaxIdAndCodeNot(String phone, String taxId);

    boolean existsByPhone(String phone);

    boolean existsByPhoneAndCodeNot(String phone, String code);

    boolean existsByLegalName(String legalName);

    boolean existsByLegalNameAndCodeNot(String legalName, String code);

}
