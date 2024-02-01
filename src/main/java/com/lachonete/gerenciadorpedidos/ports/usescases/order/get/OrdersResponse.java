package com.lachonete.gerenciadorpedidos.ports.usescases.order.get;

import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class OrdersResponse {
    @Singular("addOrder")  private List<OrderResponse> orders;

}
