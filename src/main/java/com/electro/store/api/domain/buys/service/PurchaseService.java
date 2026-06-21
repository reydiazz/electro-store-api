package com.electro.store.api.domain.buys.service;

import com.electro.store.api.domain.auth.model.entity.User;
import com.electro.store.api.domain.auth.service.AuthService;
import com.electro.store.api.domain.buys.component.PurchaseMapper;
import com.electro.store.api.domain.buys.exception.PurchaseNotFoundException;
import com.electro.store.api.domain.buys.model.entity.Purchases;
import com.electro.store.api.domain.buys.model.entity.PurchasesDetails;
import com.electro.store.api.domain.buys.model.entity.Supplier;
import com.electro.store.api.domain.buys.repository.PurchasesDetailsRepository;
import com.electro.store.api.domain.buys.repository.PurchasesRepository;
import com.electro.store.api.domain.buys.web.request.CreatePurchaseDetailRequest;
import com.electro.store.api.domain.buys.web.request.CreatePurchaseRequest;
import com.electro.store.api.domain.buys.web.response.PurchasesResponse;
import com.electro.store.api.domain.product.service.ProductService;
import com.electro.store.api.shared.utils.CodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    public static final String PREFIX = "PUR";
    public static final String DETAIL_PREFIX = "PDT";

    private final PurchasesRepository repository;
    private final PurchasesDetailsRepository detailsRepository;
    private final PurchaseMapper mapper;

    private final AuthService authService;
    private final ProductService productService;
    private final SupplierService supplierService;

    @Transactional(readOnly = true)
    public PurchasesResponse findByCode(String code) {
        Purchases purchases = findByCodeOrThrow(code);
        return mapper.toResponse(purchases);
    }

    @Transactional(readOnly = true)
    public Page<PurchasesResponse> findAll(Pageable pageable) {
        Page<Purchases> purchases = repository.findAll(pageable);
        return purchases.map(mapper::toResponse);
    }

    @Transactional
    public PurchasesResponse create(CreatePurchaseRequest request) {
        User user = authService.getAuthenticatedUser();
        Supplier supplier = supplierService.findByCodeOrThrow(
                request.supplierCode()
        );
        Purchases purchase = new Purchases(
                CodeGenerator.next(PREFIX),
                user,
                supplier,
                LocalDateTime.now()
        );
        Purchases savedPurchase = repository.save(purchase);
        for (CreatePurchaseDetailRequest detailReq : request.detail()) {
            var product = productService.findByCodeOrThrow(detailReq.productCode());
            product.increaseStock(detailReq.quantity());
            PurchasesDetails detail = new PurchasesDetails(
                    CodeGenerator.next(DETAIL_PREFIX),
                    savedPurchase,
                    product,
                    detailReq.purchasePrice(),
                    detailReq.quantity()
            );
            detailsRepository.save(detail);
        }

        return mapper.toResponse(savedPurchase);
    }

    public Purchases findByCodeOrThrow(String code) {
        return repository.findById(code)
                .orElseThrow(() -> new PurchaseNotFoundException(code));
    }
}