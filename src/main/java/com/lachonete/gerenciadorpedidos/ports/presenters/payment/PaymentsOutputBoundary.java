package com.lachonete.gerenciadorpedidos.ports.presenters.payment;


import com.lachonete.gerenciadorpedidos.ports.usescases.product.get.ProductsResponse;

public interface PaymentsOutputBoundary {
    PayamentsViewModel getViewModel();
    void present(ProductsResponse responseModel);
}
