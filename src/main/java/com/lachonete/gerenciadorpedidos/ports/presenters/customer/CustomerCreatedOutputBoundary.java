package com.lachonete.gerenciadorpedidos.ports.presenters.customer;


import com.lachonete.gerenciadorpedidos.ports.usescases.customer.add.NewCustomerResponse;

public interface CustomerCreatedOutputBoundary {
    CustomerCreatedViewModel getViewModel();
    void present(NewCustomerResponse responseModel);
}
