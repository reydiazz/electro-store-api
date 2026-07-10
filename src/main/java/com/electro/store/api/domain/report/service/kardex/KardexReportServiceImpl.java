package com.electro.store.api.domain.report.service.kardex;

import com.electro.store.api.domain.buys.repository.PurchasesDetailsRepository;
import com.electro.store.api.domain.movement.model.enums.GuideType;
import com.electro.store.api.domain.movement.repository.GuideDetailRepository;
import com.electro.store.api.domain.sales.repository.SaleDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class KardexReportServiceImpl implements KardexReportService {

    private final PurchasesDetailsRepository purchasesDetailsRepository;

    private final SaleDetailRepository saleDetailRepository;

    private final GuideDetailRepository guideDetailRepository;

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

}
