package com.lachonete.gerenciadorpedidos.ports.presenters;


import com.lachonete.gerenciadorpedidos.ports.usescases.product.get.ProductsResponse;

public interface ProductsOutputBoundary {
    ProductsViewModel getViewModel();
    void present(ProductsResponse responseModel);
}
