package com.lachonete.gerenciadorpedidos.ports.usescases.payment.get;

public interface GetPaymentInputBoundary {
    void execute(GetPaymentByIdRequest request);
}
