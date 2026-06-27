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
import com.electro.store.api.domain.buys.web.response.PurchaseDashboardResponse;
import com.electro.store.api.domain.buys.web.response.PurchaseSummaryResponse;
import com.electro.store.api.domain.buys.web.response.PurchasesResponse;
import com.electro.store.api.domain.product.service.ProductService;
import com.electro.store.api.shared.utils.CodeGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.electro.store.api.domain.buys.web.response.PurchaseMetricsResponse;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PurchaseService {

    public static final String PREFIX = "PUR";
    public static final String DETAIL_PREFIX = "PDT";
    private static final BigDecimal IGV_RATE = new BigDecimal("0.18");

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
    public Page<PurchasesResponse> findAll(String search, Pageable pageable) {
        Page<Purchases> purchases;
        if (search != null && !search.trim().isEmpty()) {
            purchases = repository.search(search.trim(), pageable);
        } else {
            purchases = repository.findAll(pageable);
        }
        return purchases.map(mapper::toResponse);
    }

    @Transactional(readOnly = true)
    public PurchaseSummaryResponse getSummary(String code) {

        Purchases purchases = findByCodeOrThrow(code);
        BigDecimal subtotal = calculatePurchasesSubtotal(purchases);
        BigDecimal igv = calculatePurchaseIgv(subtotal);
        BigDecimal total = calculatePurchaseTotal(
                subtotal,
                igv
        );
        return  new PurchaseSummaryResponse(subtotal,igv,total);
    }

    @Transactional (readOnly = true)
    public PurchaseDashboardResponse getDashboard (){
        LocalDate today = LocalDate.now();
        LocalDateTime startDate = today.atStartOfDay();
        LocalDateTime endDate = today.atTime(LocalTime.MAX);

        List<Purchases> purchases = repository.findByPurchaseDateBetween(startDate,endDate);

        Long transactions = (long) purchases.size();

        BigDecimal todayPurchase = purchases.stream().map(this::calculatePurchaseFinalAmount).reduce(BigDecimal.ZERO,BigDecimal::add);
        BigDecimal averageTicket = transactions == 0 ? BigDecimal.ZERO : todayPurchase.divide(BigDecimal.valueOf(transactions),2,RoundingMode.HALF_UP);

        return new PurchaseDashboardResponse(todayPurchase,transactions,averageTicket);
    }

    @Transactional(readOnly = true)
    public PurchaseMetricsResponse getMetrics() {
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(7);
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);

        long weeklyPurchases = repository.countWeeklyPurchases(sevenDaysAgo);
        long monthlyProducts = repository.countMonthlyProductsEntered(thirtyDaysAgo);

        String supplierName = "Ninguno";
        long supplierCount = 0;

        List<Object[]> supplierData = repository.findFrequentSupplier(PageRequest.of(0, 1));
        if (supplierData != null && !supplierData.isEmpty()) {
            Object[] row = supplierData.get(0);
            supplierName = (String) row[0];
            supplierCount = ((Number) row[1]).longValue();
        }

        return new PurchaseMetricsResponse(
                weeklyPurchases,
                monthlyProducts,
                supplierName,
                supplierCount
        );
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
            savedPurchase.addDetail(detail);
        }

        return mapper.toResponse(savedPurchase);
    }

    public Purchases findByCodeOrThrow(String code) {
        return repository.findById(code)
                .orElseThrow(() -> new PurchaseNotFoundException(code));
    }

    private BigDecimal calculatePurchasesSubtotal(Purchases purchases) {
        return purchases.getDetails().stream().map(details -> details.getPurchasePrice().multiply(BigDecimal.valueOf(details.getQuantity()))).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private BigDecimal calculatePurchaseIgv(BigDecimal subtotal) {
        return subtotal.multiply(IGV_RATE).setScale(2, RoundingMode.HALF_UP);
    }

    private BigDecimal calculatePurchaseTotal(BigDecimal subtotal, BigDecimal igv) {
        return subtotal.add(igv);
    }

    private BigDecimal calculatePurchaseFinalAmount(Purchases purchases) {
        BigDecimal subtotal = calculatePurchasesSubtotal(purchases);
        BigDecimal igv = calculatePurchaseIgv(subtotal);

        return calculatePurchaseTotal(subtotal, igv);
    }

}