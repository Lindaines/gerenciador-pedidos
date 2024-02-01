package com.lachonete.gerenciadorpedidos.ports.presenters.order;

import lombok.*;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class OrdersViewModel {
    @Singular("getOrdersViewModel") private List<OrderViewModel> orders;
}
