package com.electro.store.api.domain.sales.service;

import com.electro.store.api.domain.auth.model.entity.User;
import com.electro.store.api.domain.auth.service.AuthService;
import com.electro.store.api.domain.people.model.entity.Customer;
import com.electro.store.api.domain.people.service.CustomerService;
import com.electro.store.api.domain.product.exception.product.InsufficientStockException;
import com.electro.store.api.domain.product.model.entity.Product;
import com.electro.store.api.domain.product.service.ProductService;
import com.electro.store.api.domain.sales.component.SaleMapper;
import com.electro.store.api.domain.sales.exception.SaleNotFoundException;
import com.electro.store.api.domain.sales.model.entity.Sale;
import com.electro.store.api.domain.sales.model.entity.SaleDetail;
import com.electro.store.api.domain.sales.repository.SaleDetailRepository;
import com.electro.store.api.domain.sales.repository.SaleRepository;
import com.electro.store.api.domain.sales.web.request.CreateSaleDetailRequest;
import com.electro.store.api.domain.sales.web.request.CreateSaleRequest;
import com.electro.store.api.domain.sales.web.response.SaleResponse;
import com.electro.store.api.shared.utils.CodeGenerator;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SaleService {

    public static final String PREFIX = "SAL";
    public static final String DETAIL_PREFIX = "SDT";

    private final SaleRepository repository;
    private final SaleDetailRepository detailRepository;
    private final SaleMapper mapper;

    private final AuthService authService;
    private final ProductService productService;
    private final CustomerService customerService;

    public Sale findByCodeOrThrow(String code) {
        return repository.findById(code)
                .orElseThrow(() -> new SaleNotFoundException(code));
    }

    @Transactional(readOnly = true)
    public SaleResponse findByCode(String code) {
        Sale sale = findByCodeOrThrow(code);
        return mapper.toResponse(sale);
    }

    @Transactional(readOnly = true)
    public Page<SaleResponse> findAll(String search, Pageable pageable) {
        Page<Sale> sales;
        if (search != null && !search.trim().isEmpty()) {
            sales = repository.search(search.trim(), pageable);
        } else {
            sales = repository.findAll(pageable);
        }
        return sales.map(mapper::toResponse);
    }

    @Transactional
    public SaleResponse create(CreateSaleRequest request) {

        User user = authService.getAuthenticatedUser();

        Customer customer =
                customerService.findByCodeOrThrow(
                        request.customerCode()
                );

        Sale sale = new Sale(
                CodeGenerator.next(PREFIX),
                user,
                customer,
                LocalDateTime.now()
        );

        Sale savedSale = repository.save(sale);

        Map<String, Integer> groupedProducts =
                request.detail()
                        .stream()
                        .collect(
                                Collectors.toMap(
                                        CreateSaleDetailRequest::productCode,
                                        CreateSaleDetailRequest::quantity,
                                        Integer::sum
                                )
                        );

        for (Map.Entry<String, Integer> entry : groupedProducts.entrySet()) {

            Product product =
                    productService.findByCodeOrThrow(
                            entry.getKey()
                    );

            Integer quantity = entry.getValue();

            if (!product.hasEnoughStock(quantity)) {
                throw new InsufficientStockException(
                        product.getCode()
                );
            }

            product.decreaseStock(quantity);

            SaleDetail detail = new SaleDetail(
                    CodeGenerator.next(DETAIL_PREFIX),
                    savedSale,
                    product,
                    product.getSalePrice(),
                    quantity
            );

            detailRepository.save(detail);

            savedSale.addDetail(detail);
        }

        repository.save(savedSale);

        return mapper.toResponse(savedSale);
    }
}