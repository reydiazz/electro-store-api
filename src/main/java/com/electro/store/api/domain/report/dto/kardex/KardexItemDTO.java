package com.electro.store.api.domain.report.dto.kardex;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KardexItemDTO {

    // Código del producto
    private String productCode;

    // Nombre del producto
    private String productName;

    // Categoría del producto
    private String productCategory;

    // Fecha en la que ocurrió el movimiento
    private LocalDateTime date;

    // Tipo de movimiento (COMPRA, VENTA, GUÍA ENTRADA, GUÍA SALIDA, SALDO INICIAL)
    private String movementType;

    // Código del documento (COMP001, VENT001, GUI001, etc.)
    private String documentCode;

    // Información adicional del movimiento (Proveedor, Cliente, Motivo, etc.)
    private String detail;

    // Cantidad que ingresó al inventario
    private Integer entryQuantity;

    // Costo unitario de la entrada
    // (Costo Promedio)
    private BigDecimal entryUnitCost;

    // Total de la entrada (Cantidad × Costo Unitario)
    // (Valor Total)
    private BigDecimal entryTotal;

    // Cantidad que salió del inventario
    private Integer exitQuantity;

    // Costo unitario de la salida (Costo Promedio vigente)
    // (Costo Promedio)
    private BigDecimal exitUnitCost;

    // Total de la salida (Cantidad × Costo Unitario)
    // (Valor Total)
    private BigDecimal exitTotal;

    // Cantidad disponible luego del movimiento
    // (Saldo Inicial y actualización del saldo)
    private Integer balanceQuantity;

    // Costo promedio actual del producto
    // (Costo Promedio)
    private BigDecimal balanceUnitCost;

    // Valor total del inventario luego del movimiento
    // (Valor Total)
    private BigDecimal balanceTotal;

}
