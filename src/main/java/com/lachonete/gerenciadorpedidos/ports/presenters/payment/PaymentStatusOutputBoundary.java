package com.lachonete.gerenciadorpedidos.ports.presenters.payment;


import com.lachonete.gerenciadorpedidos.ports.usescases.payment.get.PaymentResponse;

public interface PaymentStatusOutputBoundary {
    PaymentStatusViewModel getViewModel();
    void present(PaymentResponse responseModel);
}
