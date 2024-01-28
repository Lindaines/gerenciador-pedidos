package com.lachonete.gerenciadorpedidos.ports.usescases.order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRequest {
    private UUID productId;
    private  int quantity;
    private BigDecimal price;
    private BigDecimal subtotal;
}
