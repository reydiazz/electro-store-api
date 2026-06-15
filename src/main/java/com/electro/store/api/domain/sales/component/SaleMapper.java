package com.electro.store.api.domain.sales.component;

import com.electro.store.api.domain.auth.component.UserMapper;
import com.electro.store.api.domain.people.component.CustomerMapper;
import com.electro.store.api.domain.sales.model.entity.Sale;
import com.electro.store.api.domain.sales.web.response.SaleResponse;
import jakarta.persistence.Column;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SaleMapper {

    private final UserMapper userMapper;
    private final CustomerMapper customerMapper;
    private final SaleDetailMapper saleDetailMapper;

    public SaleResponse toResponse(Sale sale){

        return new SaleResponse(
                sale.getCode(),
                userMapper.toResponse(sale.getUser()),
                customerMapper.toResponse(sale.getCustomer()),
                sale.getSaleDate(),
                sale.getDetails()
                        .stream()
                        .map(saleDetailMapper::toResponse)
                        .toList()
        );
    }
}
