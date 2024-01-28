package com.lachonete.gerenciadorpedidos.usecases.product.get;



import com.lachonete.gerenciadorpedidos.entities.Product;
import com.lachonete.gerenciadorpedidos.ports.database.ProductGateway;
import com.lachonete.gerenciadorpedidos.ports.presenters.ProductOutputBoundary;
import com.lachonete.gerenciadorpedidos.ports.presenters.ProductsOutputBoundary;
import com.lachonete.gerenciadorpedidos.ports.usescases.product.get.*;

public class GetProductById extends GetProductBase implements GetProductInputBoundary {
    private final ProductOutputBoundary presenter;
    private final ProductGateway productGateway;

    public GetProductById(ProductOutputBoundary presenter, ProductGateway productGateway) {
        this.presenter = presenter;
        this.productGateway = productGateway;
    }

    public void execute(GetProductByIdRequest request) {
        Product product = productGateway.getById(request.getId());
        if (product == null)
            throw new ProductGateway.ProductNotFoundException();
        presenter.present(makeProductResponse(product));
    }

}