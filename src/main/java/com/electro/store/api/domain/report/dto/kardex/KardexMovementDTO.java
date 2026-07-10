package com.electro.store.api.domain.report.dto.kardex;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KardexMovementDTO {

    private LocalDateTime date;

    private String movementType;

    private String documentCode;

    private String detail;

    private Integer entryQuantity;

    private Integer exitQuantity;

    private Integer balance;
}
