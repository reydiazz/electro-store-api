package com.electro.store.api.domain.buys.repository;

import com.electro.store.api.domain.buys.model.entity.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SupplierRepository extends JpaRepository<Supplier,String> {

    @Query("SELECT s FROM Supplier s WHERE " +
            "LOWER(s.code) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(s.taxId) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(s.tradeName) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(s.legalName) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<Supplier> search(@Param("search") String search, Pageable pageable);


    boolean existsByTaxId(String taxId);

    boolean existsByTaxIdAndCodeNot(String phone, String taxId);

    boolean existsByPhone(String phone);

    boolean existsByPhoneAndCodeNot(String phone, String code);

    boolean existsByLegalName(String legalName);

    boolean existsByLegalNameAndCodeNot(String legalName, String code);

}
