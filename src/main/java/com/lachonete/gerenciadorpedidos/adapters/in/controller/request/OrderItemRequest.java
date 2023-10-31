package com.lachonete.gerenciadorpedidos.adapters.in.controller.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class OrderItemRequest {
    @NotNull
    private UUID productId;
    @NotNull
    private  int quantity;
    @NotNull
    private BigDecimal price;
    @NotNull
    private BigDecimal subTotal;
}
