package com.lachonete.gerenciadorpedidos.ports.usescases.payment.get;

public interface GetCustomerInputBoundary {
    void execute(GetCustomerByIdRequest request);
}
