package com.lachonete.gerenciadorpedidos.application.core.usecase.product;

import com.lachonete.gerenciadorpedidos.application.core.domain.entity.Product;
import com.lachonete.gerenciadorpedidos.application.core.domain.exception.ProductNotFoundException;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.ProductId;
import com.lachonete.gerenciadorpedidos.application.ports.in.product.DeleteProductInputPort;
import com.lachonete.gerenciadorpedidos.application.ports.out.product.DeleteProductOutputPort;
import com.lachonete.gerenciadorpedidos.application.ports.out.product.FindProductOutputPort;

import java.util.Optional;


public class DeleteProductUseCase implements DeleteProductInputPort {
    private final DeleteProductOutputPort deleteProductOutputPort;
    private final FindProductOutputPort findProductOutputPort;

    public DeleteProductUseCase(DeleteProductOutputPort deleteProductOutputPort, FindProductOutputPort findProductOutputPort) {
        this.deleteProductOutputPort = deleteProductOutputPort;
        this.findProductOutputPort = findProductOutputPort;
    }

    @Override
    public void delete(ProductId productId) {
        Optional<Product> productSavedOptional = findProductOutputPort.findById(productId);
        if(productSavedOptional.isEmpty()){
            throw new ProductNotFoundException("Product Not Found");
        }
        deleteProductOutputPort.delete(productId);
    }

}
