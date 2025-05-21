package com.lachonete.gerenciadorpedidos.api.endpoints.order;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class OrderItemRequest {
    private UUID productId;
    private  int quantity;
    private BigDecimal price;
    private BigDecimal subtotal;
}
