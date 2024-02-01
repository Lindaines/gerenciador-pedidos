package com.lachonete.gerenciadorpedidos.ports.presenters.order;


import com.lachonete.gerenciadorpedidos.ports.usescases.order.get.OrdersResponse;

public interface OrdersOutputBoundary {
    OrdersViewModel getViewModel();
    void present(OrdersResponse responseModel);
}
