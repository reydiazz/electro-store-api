package com.electro.store.api.domain.buys.component;

import com.electro.store.api.domain.buys.model.entity.Suppliers;
import com.electro.store.api.domain.buys.web.response.SupplierResponse;
import org.springframework.stereotype.Component;

@Component
public class SupplierMapper {

    public SupplierResponse toResponse(Suppliers suppliers){
        return  new SupplierResponse(
                suppliers.getCode(),
                suppliers.getTaxId(),
                suppliers.getTradeName(),
                suppliers.getPhone(),
                suppliers.getLegalName()
        );
    }
}
