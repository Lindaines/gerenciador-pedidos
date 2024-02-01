package com.lachonete.gerenciadorpedidos.usecases.product.remove;


import com.lachonete.gerenciadorpedidos.ports.database.ProductGateway;
import com.lachonete.gerenciadorpedidos.ports.usescases.product.remove.RemoveProductInputBoundary;
import com.lachonete.gerenciadorpedidos.ports.usescases.product.remove.RemoveProductRequest;

public class RemoveProduct  implements RemoveProductInputBoundary {
    private final ProductGateway productGateway;

    public RemoveProduct(ProductGateway productGateway) {
        this.productGateway = productGateway;
    }

    @Override
    public void execute(RemoveProductRequest request) {
        productGateway.delete(request.getId());

    }
}
