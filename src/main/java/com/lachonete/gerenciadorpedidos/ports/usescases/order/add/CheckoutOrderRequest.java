package com.lachonete.gerenciadorpedidos.ports.usescases.order.add;

import lombok.*;

import java.util.List;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class CheckoutOrderRequest {
        List<OrderItemRequest> items;
    }

