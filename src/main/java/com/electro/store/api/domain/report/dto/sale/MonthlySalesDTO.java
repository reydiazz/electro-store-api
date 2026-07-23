package com.electro.store.api.domain.report.dto.sale;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class MonthlySalesDTO {

    private String month;
    // Ventas totales del mes
    private BigDecimal totalRevenue;
    // Diferencia de dinero respecto al anterior
    private BigDecimal absoluteGrowth;
    // El porcentaje de crecimiento respecto al mes anterior
    private BigDecimal increase;
    // EL porcentaje de la ganancia total del mes en el año
    private Double percentage;

}
