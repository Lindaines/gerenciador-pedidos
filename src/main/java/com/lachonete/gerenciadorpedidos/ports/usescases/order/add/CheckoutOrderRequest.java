package com.lachonete.gerenciadorpedidos.ports.usescases.order.add;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class CheckoutOrderRequest {
        List<OrderItemRequest> items;
    }

