package com.electro.store.api.domain.movement.service;

import com.electro.store.api.domain.auth.model.entity.User;
import com.electro.store.api.domain.auth.service.AuthService;
import com.electro.store.api.domain.movement.component.InventoryGuideMapper;
import com.electro.store.api.domain.movement.exception.InventoryGuideNotFoundException;
import com.electro.store.api.domain.movement.model.entity.GuideDetail;
import com.electro.store.api.domain.movement.model.entity.InventoryGuide;
import com.electro.store.api.domain.movement.model.enums.GuideType;
import com.electro.store.api.domain.movement.repository.GuideDetailRepository;
import com.electro.store.api.domain.movement.repository.InventoryGuideRepository;
import com.electro.store.api.domain.movement.web.request.CreateGuideDetailRequest;
import com.electro.store.api.domain.movement.web.request.CreateInventoryGuideRequest;
import com.electro.store.api.domain.movement.web.response.InventoryGuideResponse;
import com.electro.store.api.domain.product.exception.product.InsufficientStockException;
import com.electro.store.api.domain.product.model.entity.Product;
import com.electro.store.api.domain.product.service.ProductService;
import com.electro.store.api.shared.utils.CodeGenerator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class InventoryGuideService {

    public static final String PREFIX = "GUI";
    public static final String DETAIL_PREFIX = "GDT";

    private final InventoryGuideRepository repository;
    private final GuideDetailRepository detailRepository;
    private final InventoryGuideMapper mapper;

    private final AuthService authService;
    private final ProductService productService;

    public InventoryGuide findByCodeOrThrow(String code) {
        return repository.findById(code).orElseThrow(
                () -> new InventoryGuideNotFoundException(code)
        );
    }

    @Transactional(readOnly = true)
    public InventoryGuideResponse findByCode(String code) {
        InventoryGuide guide = findByCodeOrThrow(code);
        return mapper.toResponse(guide);
    }

    @Transactional
    public InventoryGuideResponse create(CreateInventoryGuideRequest request) {

        User user = authService.getAuthenticatedUser();

        InventoryGuide guide = new InventoryGuide(
                CodeGenerator.next(PREFIX),
                user,
                request.type(),
                request.reason(),
                request.description(),
                LocalDateTime.now()
        );

        InventoryGuide savedGuide = repository.save(guide);

        for (CreateGuideDetailRequest detailRequest : request.detail()) {

            Product product = productService.findByCodeOrThrow(
                    detailRequest.productCode()
            );

            Integer quantity = detailRequest.quantity();

            if (request.type() == GuideType.ENTRY) {

                product.increaseStock(quantity);

            } else {

                if (!product.hasEnoughStock(quantity)) {
                    throw new InsufficientStockException(
                            product.getCode()
                    );
                }

                product.decreaseStock(quantity);
            }

            GuideDetail detail = new GuideDetail(
                    CodeGenerator.next(DETAIL_PREFIX),
                    savedGuide,
                    product,
                    quantity
            );

            detailRepository.save(detail);

            savedGuide.addDetail(detail);
        }

        repository.save(savedGuide);

        return mapper.toResponse(savedGuide);
    }

    @Transactional(readOnly = true)
    public Page<InventoryGuideResponse> findAll(
            String search,
            GuideType type,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Pageable pageable
    ) {
        Page<InventoryGuide> guides;
        if ((search != null && !search.trim().isEmpty()) || type != null || startDate != null || endDate != null) {
            guides = repository.search(
                    search != null ? search.trim() : null,
                    type,
                    startDate,
                    endDate,
                    pageable
            );
        } else {
            guides = repository.findAll(pageable);
        }
        return guides.map(mapper::toResponse);
    }
}
