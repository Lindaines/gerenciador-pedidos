package com.lachonete.gerenciadorpedidos.ports.presenters.product;


import com.lachonete.gerenciadorpedidos.ports.usescases.product.get.ProductsResponse;

public interface ProductsOutputBoundary {
    ProductsViewModel getViewModel();
    void present(ProductsResponse responseModel);
}
