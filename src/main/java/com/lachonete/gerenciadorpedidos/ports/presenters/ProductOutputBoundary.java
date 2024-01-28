package com.lachonete.gerenciadorpedidos.ports.presenters;


import com.lachonete.gerenciadorpedidos.ports.usescases.product.get.ProductResponse;
import com.lachonete.gerenciadorpedidos.ports.usescases.product.get.ProductsResponse;

public interface ProductOutputBoundary {
    ProductViewModel getViewModel();
    void present(ProductResponse responseModel);
}
