package com.lachonete.gerenciadorpedidos.api.endpoints.order;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
public class NewOrderRequest {
    private List<OrderItemRequest> items;
}
