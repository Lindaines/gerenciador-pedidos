package com.lachonete.gerenciadorpedidos.ports.presenters.order;


import com.lachonete.gerenciadorpedidos.ports.usescases.order.add.CheckoutOrderResponse;

public interface OrderCreatedOutputBoundary {
    OrderCreatedViewModel getViewModel();
    void present(CheckoutOrderResponse responseModel);
}
