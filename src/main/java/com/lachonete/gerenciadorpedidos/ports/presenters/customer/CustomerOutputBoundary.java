package com.lachonete.gerenciadorpedidos.ports.presenters.customer;


import com.lachonete.gerenciadorpedidos.ports.usescases.product.get.ProductResponse;

public interface CustomerOutputBoundary {
    CustomerViewModel getViewModel();
    void present(ProductResponse responseModel);
}
