package com.electro.store.api.domain.report.service.kardex;

import com.electro.store.api.domain.buys.repository.PurchasesDetailsRepository;
import com.electro.store.api.domain.movement.model.enums.GuideType;
import com.electro.store.api.domain.movement.repository.GuideDetailRepository;
import com.electro.store.api.domain.product.exception.product.ProductNotFoundException;
import com.electro.store.api.domain.product.model.entity.Product;
import com.electro.store.api.domain.product.repository.ProductRepository;
import com.electro.store.api.domain.sales.repository.SaleDetailRepository;
import com.electro.store.api.domain.report.dto.kardex.KardexItemDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KardexReportServiceImpl implements KardexReportService {

    private static final int COST_SCALE = 4;

    private final PurchasesDetailsRepository purchasesDetailsRepository;
    private final SaleDetailRepository saleDetailRepository;
    private final GuideDetailRepository guideDetailRepository;
    private final ProductRepository productRepository;

    private record RawMovement(
            String productCode,
            LocalDateTime date,
            String movementType,
            String documentCode,
            String detail,
            int quantity,
            BigDecimal unitCost,
            boolean entry
    ) {
    }

    @Override
    @Transactional(readOnly = true)
    public List<KardexItemDTO> generateKardex(
            String productCode,
            LocalDateTime startDate,
            LocalDateTime endDate
    ) {

        if (productCode != null && !productRepository.existsById(productCode)) {
            throw new ProductNotFoundException(productCode);
        }

        Map<String, Product> products = productRepository.findAll().stream()
                .collect(Collectors.toMap(Product::getCode, Function.identity()));

        List<RawMovement> movements = loadAllMovementsUntil(productCode, endDate);

        Map<String, List<RawMovement>> byProduct = movements.stream()
                .collect(Collectors.groupingBy(RawMovement::productCode,
                        LinkedHashMap::new, Collectors.toList()));

        List<KardexItemDTO> kardex = new ArrayList<>();
        byProduct.forEach((code, productMovements) -> {
            Product product = products.get(code);
            if (product != null) {
                kardex.addAll(walkProduct(product, productMovements, startDate));
            }
        });
        return kardex;
    }

    private List<RawMovement> loadAllMovementsUntil(String productCode, LocalDateTime endDate) {

        List<RawMovement> movements = new ArrayList<>();

        purchasesDetailsRepository.findPurchaseMovementsUntil(productCode, endDate).forEach(p ->
                movements.add(new RawMovement(p.productCode(), p.date(), "COMPRA", p.documentCode(),
                        label("Proveedor", p.supplierName()), p.quantity(), p.unitCost(), true)));

        saleDetailRepository.findSaleMovementsUntil(productCode, endDate).forEach(s ->
                movements.add(new RawMovement(s.productCode(), s.date(), "VENTA", s.documentCode(),
                        label("Cliente", s.customerName()), s.quantity(), null, false)));

        guideDetailRepository.findGuideMovementsUntil(productCode, endDate).forEach(g -> {
            boolean entry = g.type() == GuideType.ENTRY;
            movements.add(new RawMovement(g.productCode(), g.date(),
                    entry ? "GUÍA ENTRADA" : "GUÍA SALIDA", g.documentCode(),
                    label("Motivo", g.reason()), g.quantity(), null, entry));
        });

        movements.sort(Comparator.comparing(RawMovement::productCode)
                .thenComparing(RawMovement::date)
                .thenComparing(m -> m.entry() ? 0 : 1));

        return movements;
    }

    private List<KardexItemDTO> walkProduct(
            Product product,
            List<RawMovement> movements,
            LocalDateTime startDate
    ) {

        int quantity = 0;
        BigDecimal totalValue = BigDecimal.ZERO;
        boolean initialEmitted = false;
        List<KardexItemDTO> rows = new ArrayList<>();

        for (RawMovement movement : movements) {

            boolean inPeriod = !movement.date().isBefore(startDate);

            if (inPeriod && !initialEmitted) {
                rows.add(initialBalanceRow(product, startDate, quantity, totalValue));
                initialEmitted = true;
            }

            BigDecimal averageBefore = averageCost(totalValue, quantity);
            BigDecimal movementCost;

            if (movement.entry()) {
                movementCost = movement.unitCost() != null ? movement.unitCost() : averageBefore;
                quantity += movement.quantity();
                totalValue = totalValue.add(
                        movementCost.multiply(BigDecimal.valueOf(movement.quantity())));
            } else {
                movementCost = averageBefore;
                quantity -= movement.quantity();
                totalValue = totalValue.subtract(
                        movementCost.multiply(BigDecimal.valueOf(movement.quantity())));
            }
            if (quantity <= 0) {
                totalValue = BigDecimal.ZERO;
            }

            if (inPeriod) {
                rows.add(movementRow(product, movement, movementCost, quantity, totalValue));
            }
        }

        if (!initialEmitted && quantity != 0) {
            rows.add(initialBalanceRow(product, startDate, quantity, totalValue));
        }

        return rows;
    }

    private KardexItemDTO initialBalanceRow(
            Product product, LocalDateTime startDate, int quantity, BigDecimal totalValue) {

        KardexItemDTO item = new KardexItemDTO();
        fillProduct(item, product);
        item.setDate(startDate);
        item.setMovementType("SALDO INICIAL");
        item.setDocumentCode("-");
        item.setDetail("Saldo antes del período seleccionado");
        item.setBalanceQuantity(quantity);
        item.setBalanceUnitCost(averageCost(totalValue, quantity));
        item.setBalanceTotal(totalValue);
        return item;
    }

    private KardexItemDTO movementRow(
            Product product, RawMovement movement, BigDecimal movementCost,
            int balanceQuantity, BigDecimal balanceValue) {

        BigDecimal movementTotal = movementCost.multiply(BigDecimal.valueOf(movement.quantity()));

        KardexItemDTO item = new KardexItemDTO();
        fillProduct(item, product);
        item.setDate(movement.date());
        item.setMovementType(movement.movementType());
        item.setDocumentCode(movement.documentCode());
        item.setDetail(movement.detail());

        if (movement.entry()) {
            item.setEntryQuantity(movement.quantity());
            item.setEntryUnitCost(movementCost);
            item.setEntryTotal(movementTotal);
        } else {
            item.setExitQuantity(movement.quantity());
            item.setExitUnitCost(movementCost);
            item.setExitTotal(movementTotal);
        }

        item.setBalanceQuantity(balanceQuantity);
        item.setBalanceUnitCost(averageCost(balanceValue, balanceQuantity));
        item.setBalanceTotal(balanceValue);
        return item;
    }

    private void fillProduct(KardexItemDTO item, Product product) {
        item.setProductCode(product.getCode());
        item.setProductName(product.getName());
        item.setProductCategory(product.getCategory().getName());
    }

    private BigDecimal averageCost(BigDecimal totalValue, int quantity) {
        if (quantity <= 0) {
            return BigDecimal.ZERO;
        }
        return totalValue.divide(BigDecimal.valueOf(quantity), COST_SCALE, RoundingMode.HALF_UP);
    }

    private String label(String prefix, String value) {
        return value == null || value.isBlank() ? "" : prefix + ": " + value;
    }

}
