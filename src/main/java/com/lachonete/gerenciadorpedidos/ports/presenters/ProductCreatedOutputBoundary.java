package com.lachonete.gerenciadorpedidos.ports.presenters;


import com.lachonete.gerenciadorpedidos.ports.usescases.product.add.NewProductResponse;

public interface ProductCreatedOutputBoundary {
    ProductCreatedViewModel getViewModel();
    void present(NewProductResponse responseModel);
}
