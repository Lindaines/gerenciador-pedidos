package com.lachonete.gerenciadorpedidos.ports.presenters.product;


import com.lachonete.gerenciadorpedidos.ports.usescases.product.get.ProductResponse;

public interface ProductOutputBoundary {
    ProductViewModel getViewModel();
    void present(ProductResponse responseModel);
}
