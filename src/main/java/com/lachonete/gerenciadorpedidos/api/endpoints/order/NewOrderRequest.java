package com.lachonete.gerenciadorpedidos.api.endpoints.order;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@Builder
public class NewOrderRequest {
    private List<OrderItemRequest> items;
}
