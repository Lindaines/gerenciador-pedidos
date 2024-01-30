package com.lachonete.gerenciadorpedidos.usecases.payment.get;



import com.lachonete.gerenciadorpedidos.ports.database.ProductGateway;
import com.lachonete.gerenciadorpedidos.ports.presenters.product.ProductsOutputBoundary;
import com.lachonete.gerenciadorpedidos.ports.usescases.product.get.GetProductsInputBoundary;
import com.lachonete.gerenciadorpedidos.ports.usescases.product.get.ProductsResponse;

public class GetPayments extends GetPaymentBase implements GetProductsInputBoundary {
    private final ProductsOutputBoundary presenter;
    private final ProductGateway productGateway;

    public GetPayments(ProductsOutputBoundary presenter, ProductGateway productGateway) {
        this.presenter = presenter;
        this.productGateway = productGateway;
    }

    public void execute() {

    }

}