package com.electro.store.api.domain.report.service.kardex;

import com.electro.store.api.domain.buys.repository.PurchasesDetailsRepository;
import com.electro.store.api.domain.movement.model.enums.GuideType;
import com.electro.store.api.domain.movement.repository.GuideDetailRepository;
import com.electro.store.api.domain.product.model.entity.Product;
import com.electro.store.api.domain.product.repository.ProductRepository;
import com.electro.store.api.domain.sales.repository.SaleDetailRepository;
import com.electro.store.api.domain.report.dto.kardex.KardexItemDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KardexReportServiceImpl implements KardexReportService {

    private final PurchasesDetailsRepository purchasesDetailsRepository;

    private final SaleDetailRepository saleDetailRepository;

    private final GuideDetailRepository guideDetailRepository;

    private final ProductRepository productRepository;

    @Override
    @Transactional(readOnly = true)
    public Integer calculateInitialBalance(
            String productCode,
            LocalDateTime startDate
    ) {

        Integer purchasedQuantity =
                purchasesDetailsRepository.getPurchasedQuantityBefore(
                        productCode,
                        startDate
                );

        Integer soldQuantity =
                saleDetailRepository.getSoldQuantityBefore(
                        productCode,
                        startDate
                );

        Integer guideEntryQuantity =
                guideDetailRepository.getGuideQuantityBefore(
                        productCode,
                        startDate,
                        GuideType.ENTRY
                );

        Integer guideExitQuantity =
                guideDetailRepository.getGuideQuantityBefore(
                        productCode,
                        startDate,
                        GuideType.EXIT
                );

        return purchasedQuantity
                + guideEntryQuantity
                - soldQuantity
                - guideExitQuantity;
    }

    @Override
    @Transactional(readOnly = true)
    public List<KardexItemDTO> generateKardex(
            String productCode,
            LocalDateTime startDate,
            LocalDateTime endDate
    ) {

        Product product = productRepository.findById(productCode)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Producto no encontrado: " + productCode
                ));

        Integer balance = calculateInitialBalance(
                productCode,
                startDate
        );

        List<KardexItemDTO> kardex = new ArrayList<>();

        kardex.add(
                buildInitialBalanceItem(
                        product,
                        balance,
                        startDate
                )
        );

        return kardex;
    }

    private KardexItemDTO buildInitialBalanceItem(
            Product product,
            Integer balance,
            LocalDateTime startDate
    ) {

        KardexItemDTO item = new KardexItemDTO();

        // =========================
        // Información del producto
        // =========================

        item.setProductCode(product.getCode());
        item.setProductName(product.getName());
        item.setProductCategory(product.getCategory().getName());

        // =========================
        // Movimiento
        // =========================

        item.setDate(startDate);
        item.setMovementType("SALDO INICIAL");
        item.setDocumentCode("");
        item.setDetail("Saldo antes del período seleccionado");

        // =========================
        // Entradas
        // =========================

        item.setEntryQuantity(0);
        item.setEntryUnitCost(null);
        item.setEntryTotal(null);

        // =========================
        // Salidas
        // =========================

        item.setExitQuantity(0);
        item.setExitUnitCost(null);
        item.setExitTotal(null);

        // =========================
        // Saldo
        // =========================

        item.setBalanceQuantity(balance);
        item.setBalanceUnitCost(null);
        item.setBalanceTotal(null);

        return item;
    }

}
