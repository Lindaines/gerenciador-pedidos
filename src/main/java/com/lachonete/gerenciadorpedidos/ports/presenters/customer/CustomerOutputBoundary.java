package com.lachonete.gerenciadorpedidos.ports.presenters.customer;


import com.lachonete.gerenciadorpedidos.ports.usescases.customer.get.CustomerResponse;

public interface CustomerOutputBoundary {
    CustomerViewModel getViewModel();
    void present(CustomerResponse responseModel);
}
