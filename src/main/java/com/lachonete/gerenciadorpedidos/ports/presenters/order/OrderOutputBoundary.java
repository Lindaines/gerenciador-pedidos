package com.lachonete.gerenciadorpedidos.ports.presenters.order;


import com.lachonete.gerenciadorpedidos.ports.presenters.customer.CustomerViewModel;
import com.lachonete.gerenciadorpedidos.ports.usescases.customer.get.CustomerResponse;
import com.lachonete.gerenciadorpedidos.ports.usescases.order.get.OrderResponse;

public interface OrderOutputBoundary {
    OrderViewModel getViewModel();
    void present(OrderResponse responseModel);
}
