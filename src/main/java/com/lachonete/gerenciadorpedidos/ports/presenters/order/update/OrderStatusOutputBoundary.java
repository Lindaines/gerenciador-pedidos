package com.lachonete.gerenciadorpedidos.ports.presenters.order.update;


import com.lachonete.gerenciadorpedidos.ports.usescases.payment.get.PaymentResponse;

public interface OrderStatusOutputBoundary {
    OrderStatusViewModel getViewModel();
    void present(PaymentResponse responseModel);
}
