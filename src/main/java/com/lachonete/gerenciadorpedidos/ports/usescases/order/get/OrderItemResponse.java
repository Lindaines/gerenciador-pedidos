package com.lachonete.gerenciadorpedidos.ports.usescases.order.get;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderItemResponse {
    private Long id;
    private UUID productId;
    private BigDecimal subTotal;
    private BigDecimal price;
    private int  quantity;
}
