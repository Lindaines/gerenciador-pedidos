package com.lachonete.gerenciadorpedidos.ports.presenters.order;


import com.lachonete.gerenciadorpedidos.ports.presenters.customer.CustomerViewModel;
import com.lachonete.gerenciadorpedidos.ports.usescases.customer.get.CustomerResponse;

public interface OrderOutputBoundary {
    CustomerViewModel getViewModel();
    void present(CustomerResponse responseModel);
}
