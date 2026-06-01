package com.electro.store.api.domain.buys.service;


import com.electro.store.api.domain.buys.component.SupplierMapper;
import com.electro.store.api.domain.buys.exception.SupplierNotFoundException;
import com.electro.store.api.domain.buys.model.entity.Suppliers;
import com.electro.store.api.domain.buys.repository.SupplierRepository;
import com.electro.store.api.domain.buys.web.request.CreateSupplierRequest;
import com.electro.store.api.domain.buys.web.request.UpdateSupplierRequest;
import com.electro.store.api.domain.buys.web.response.SupplierResponse;
import com.electro.store.api.shared.utils.CodeGenerator;
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
    public final SupplierMapper mapper;

    @Transactional(readOnly = true)
    public Page<SupplierResponse> findAll(Pageable pageable) {
        Page<Suppliers> suppliers = repository.findAll(pageable);
        return suppliers.map(mapper::toResponse);
    }

    @Transactional
    public SupplierResponse create(CreateSupplierRequest request) {
        String code = CodeGenerator.next(PREFIX);
        Suppliers suppliers = new Suppliers(
                code,
                request.taxId(),
                request.tradeName(),
                request.phone(),
                request.legalName()
        );
        Suppliers saved = repository.save(suppliers);
        return mapper.toResponse(saved);
    }

    @Transactional
    public SupplierResponse update(String code, UpdateSupplierRequest request) {
        Suppliers suppliers = findByCodeOrThrow(code);
        suppliers.update(
                request.taxId(),
                request.tradeName(),
                request.phone(),
                request.legalName()
        );
        return mapper.toResponse(suppliers);
    }

    @Transactional
    public void delete(String code) {
        Suppliers suppliers = findByCodeOrThrow(code);
        repository.delete(suppliers);
    }


    public Suppliers findByCodeOrThrow(String code) {
        return repository.findById(code)
                .orElseThrow(() -> new SupplierNotFoundException(code));
    }

}
