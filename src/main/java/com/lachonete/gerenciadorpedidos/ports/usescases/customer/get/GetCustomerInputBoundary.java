package com.lachonete.gerenciadorpedidos.ports.usescases.customer.get;

public interface GetCustomerInputBoundary {
    void execute(GetCustomerByIdRequest request);
}
