package com.lachonete.gerenciadorpedidos.adapters.in.controller.request;

import lombok.Data;

import java.util.UUID;

@Data
public class OrderItemRequest {
    private UUID productId;
    private  int quantity;
}
