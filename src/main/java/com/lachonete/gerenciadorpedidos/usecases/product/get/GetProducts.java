package com.lachonete.gerenciadorpedidos.usecases.product.get;



import com.lachonete.gerenciadorpedidos.ports.database.ProductGateway;
import com.lachonete.gerenciadorpedidos.ports.presenters.product.ProductsOutputBoundary;
import com.lachonete.gerenciadorpedidos.ports.usescases.product.get.GetProductsInputBoundary;
import com.lachonete.gerenciadorpedidos.ports.usescases.product.get.ProductsResponse;

public class GetProducts extends GetProductBase implements GetProductsInputBoundary {
    private final ProductsOutputBoundary presenter;
    private final ProductGateway productGateway;

    public GetProducts(ProductsOutputBoundary presenter, ProductGateway productGateway) {
        this.presenter = presenter;
        this.productGateway = productGateway;
    }

    public void execute() {
        ProductsResponse.ProductsResponseBuilder result = ProductsResponse.builder();
        productGateway.getAll().forEach(product -> result.addProduct(makeProductResponse(product)));
        presenter.present(result.build());
    }

}