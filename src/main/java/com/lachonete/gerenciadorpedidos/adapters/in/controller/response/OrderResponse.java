package com.lachonete.gerenciadorpedidos.adapters.in.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class OrderResponse {
    private UUID id;
    private BigDecimal price;
    private String status;
}
