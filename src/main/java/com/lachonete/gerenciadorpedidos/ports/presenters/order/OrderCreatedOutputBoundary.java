package com.lachonete.gerenciadorpedidos.ports.presenters.order;


import com.lachonete.gerenciadorpedidos.ports.usescases.order.CheckoutOrderResponse;

public interface OrderCreatedOutputBoundary {
    OrderCreatedViewModel getViewModel();
    void present(CheckoutOrderResponse responseModel);
}
