package com.lachonete.gerenciadorpedidos.application.core.usecase.product;

import com.lachonete.gerenciadorpedidos.application.core.domain.entity.Product;
import com.lachonete.gerenciadorpedidos.application.core.domain.exception.ProductNotFoundException;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.ProductCategory;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.ProductId;
import com.lachonete.gerenciadorpedidos.application.ports.in.product.FindProductByIdInputPort;
import com.lachonete.gerenciadorpedidos.application.ports.out.product.FindProductOutputPort;
import com.lachonete.gerenciadorpedidos.application.ports.out.product.ListProductsByCategoryOutputPort;

import java.util.List;


public class FindProductByIdUseCase implements FindProductByIdInputPort {
    private final FindProductOutputPort findProductOutputPort;

    public FindProductByIdUseCase(FindProductOutputPort findProductOutputPort) {
        this.findProductOutputPort = findProductOutputPort;
    }

    @Override
    public Product findById(ProductId productId) {
        var product = findProductOutputPort.findById(productId);
        if (product.isEmpty()){
            throw new ProductNotFoundException("Product Not Found");
        }
        return product.get();
    }
}
