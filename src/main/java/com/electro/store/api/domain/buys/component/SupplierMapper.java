package com.electro.store.api.domain.buys.component;

import com.electro.store.api.domain.buys.model.entity.Supplier;
import com.electro.store.api.domain.buys.web.response.SupplierResponse;
import org.springframework.stereotype.Component;

@Component
public class SupplierMapper {

    public SupplierResponse toResponse(Supplier supplier) {
        return new SupplierResponse(
                supplier.getCode(),
                supplier.getTaxId(),
                supplier.getTradeName(),
                supplier.getPhone(),
                supplier.getLegalName()
        );
    }
}
