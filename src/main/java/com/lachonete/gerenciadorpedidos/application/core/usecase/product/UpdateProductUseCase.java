package com.lachonete.gerenciadorpedidos.application.core.usecase.product;

import com.lachonete.gerenciadorpedidos.application.core.domain.entity.Product;
import com.lachonete.gerenciadorpedidos.application.core.domain.exception.ProductNotFoundException;
import com.lachonete.gerenciadorpedidos.application.ports.in.product.UpdateProductInputPort;
import com.lachonete.gerenciadorpedidos.application.ports.out.product.FindProductOutputPort;
import com.lachonete.gerenciadorpedidos.application.ports.out.product.UpdateProductOutputPort;

import java.util.Optional;


public class UpdateProductUseCase implements UpdateProductInputPort {
    private final UpdateProductOutputPort updateProductOutputPort;
    private final FindProductOutputPort findProductOutputPort;

    public UpdateProductUseCase(UpdateProductOutputPort updateProductOutputPort, FindProductOutputPort findProductOutputPort) {
        this.updateProductOutputPort = updateProductOutputPort;
        this.findProductOutputPort = findProductOutputPort;
    }


    @Override
    public void update(Product product) {
        Optional<Product> productSavedOptional = findProductOutputPort.find(product);
        if(productSavedOptional.isEmpty()){
            throw new ProductNotFoundException("Product Not Found");
        }
        var productSaved = productSavedOptional.get();
        productSaved.setName(product.getName());
        productSaved.setPrice(product.getPrice());
        productSaved.setCategory(product.getCategory());
        productSaved.setDescription(product.getDescription());
        productSaved.setImages(product.getImages());
        productSaved.setId(product.getId());
        updateProductOutputPort.update(productSaved);
    }
}
