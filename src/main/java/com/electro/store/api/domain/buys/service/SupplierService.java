package com.electro.store.api.domain.buys.service;

import com.electro.store.api.domain.buys.exception.SupplierLegalNameAlreadyExists;
import com.electro.store.api.domain.buys.exception.SupplierNotFoundException;
import com.electro.store.api.domain.buys.exception.SupplierPhoneAlreadyExists;
import com.electro.store.api.domain.buys.exception.SupplierTaxIdAlreadyExists;
import com.electro.store.api.domain.buys.model.entity.Supplier;
import com.electro.store.api.domain.buys.repository.SupplierRepository;
import com.electro.store.api.domain.buys.web.request.CreateSupplierRequest;
import com.electro.store.api.domain.buys.web.request.UpdateSupplierRequest;
import com.electro.store.api.shared.utils.CodeGenerator;
import com.electro.store.api.domain.buys.web.response.SupplierMetricsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SupplierService {

    public static final String PREFIX = "SUP";
    public final SupplierRepository repository;

    @Transactional(readOnly = true)
    public SupplierMetricsResponse getMetrics() {
        long total = repository.count();
        Supplier last = repository.findFirstByOrderByCodeDesc();
        String lastSupplierName = (last != null) ? last.getTradeName() : "Ninguno";
        return new SupplierMetricsResponse(total, lastSupplierName);
    }

    @Transactional(readOnly = true)
    public Page<Supplier> findAll(String search, Pageable pageable) {
        if (search != null && !search.trim().isEmpty()) {
            return repository.search(search.trim(), pageable);
        }
        return repository.findAll(pageable);
    }

    @Transactional
    public Supplier create(CreateSupplierRequest request) {
        verifyLegalName(request.legalName());
        verifyPhone(request.phone());
        verifyTaxId(request.taxId());
        String code = CodeGenerator.next(PREFIX);
        Supplier supplier = new Supplier(code, request.taxId(), request.tradeName(), request.phone(), request.legalName());
        return repository.save(supplier);
    }

    @Transactional
    public Supplier update(String code, UpdateSupplierRequest request) {
        verifyLegalName(request.legalName(), code);
        verifyPhone(request.phone(), code);
        verifyTaxId(request.taxId(), code);
        Supplier supplier = findByCodeOrThrow(code);
        supplier.update(request.taxId(), request.tradeName(), request.phone(), request.legalName());
        return supplier;
    }

    @Transactional
    public void delete(String code) {
        Supplier supplier = findByCodeOrThrow(code);
        repository.delete(supplier);
    }

    public Supplier findByCodeOrThrow(String code) {
        return repository.findById(code).orElseThrow(
                () -> new SupplierNotFoundException(code)
        );
    }

    private void verifyPhone(String phone) {
        if (repository.existsByPhone(phone)) {
            throw new SupplierPhoneAlreadyExists(phone);
        }
    }

    private void verifyPhone(String phone, String code) {
        if (repository.existsByPhoneAndCodeNot(phone, code)) {
            throw new SupplierPhoneAlreadyExists(phone);
        }
    }

    private void verifyTaxId(String taxId) {
        if (repository.existsByTaxId(taxId)) {
            throw new SupplierTaxIdAlreadyExists(taxId);
        }
    }

    private void verifyTaxId(String taxId, String code) {
        if (repository.existsByTaxIdAndCodeNot(taxId, code)) {
            throw new SupplierTaxIdAlreadyExists(taxId);
        }
    }

    private void verifyLegalName(String name) {
        if (repository.existsByLegalName(name)) {
            throw new SupplierLegalNameAlreadyExists(name);
        }
    }

    private void verifyLegalName(String name, String code) {
        if (repository.existsByLegalNameAndCodeNot(name, code)) {
            throw new SupplierLegalNameAlreadyExists(name);
        }
    }

}
