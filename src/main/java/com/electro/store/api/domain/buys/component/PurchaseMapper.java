package com.electro.store.api.domain.buys.component;


import com.electro.store.api.domain.auth.component.UserMapper;
import com.electro.store.api.domain.buys.model.entity.Purchases;
import com.electro.store.api.domain.buys.web.response.PurchasesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PurchaseMapper {

    private final UserMapper userMapper;
    private final SupplierMapper supplierMapper;
    private  final PurchaseDetailMapper purchaseDetailMapper;

    public PurchasesResponse toResponse (Purchases purchases){
        return new PurchasesResponse(
                purchases.getCode(),
                userMapper.toResponse(purchases.getUser()),
                supplierMapper.toResponse(purchases.getSupplier()),
                purchases.getPurchaseDate(),
                purchases.getDetails()
                        .stream()
                        .map(purchaseDetailMapper::toResponse)
                        .toList()
        );

    }
}
