package com.lachonete.gerenciadorpedidos.usecases.product.add;


import com.lachonete.gerenciadorpedidos.entities.Product;
import com.lachonete.gerenciadorpedidos.entities.valueobject.Image;
import com.lachonete.gerenciadorpedidos.entities.valueobject.Money;
import com.lachonete.gerenciadorpedidos.ports.database.ProductGateway;
import com.lachonete.gerenciadorpedidos.ports.presenters.product.ProductCreatedOutputBoundary;
import com.lachonete.gerenciadorpedidos.ports.usescases.product.add.AddProductInputBoundary;
import com.lachonete.gerenciadorpedidos.ports.usescases.product.add.AddProductRequest;
import com.lachonete.gerenciadorpedidos.ports.usescases.product.add.NewProductResponse;

import java.util.UUID;

public class AddProduct implements AddProductInputBoundary {
    private final ProductCreatedOutputBoundary presenter;
    private final ProductGateway productGateway;

    public AddProduct(ProductCreatedOutputBoundary presenter, ProductGateway productGateway) {
        this.presenter = presenter;
        this.productGateway = productGateway;
    }

    public void execute(AddProductRequest request) {
        validateProduct(request);
        UUID id = addProduct(request);

        NewProductResponse responseModel = new NewProductResponse(id);
        presenter.present(responseModel);
    }

    private void validateProduct(final AddProductRequest request) {
        if (productAlreadyExists(request))
            throw new ProductGateway.ProductAlreadyByNameExistsException();
    }

    private boolean productAlreadyExists(final AddProductRequest request) {
        // To do: Validation
        return false;
    }

    private UUID addProduct(AddProductRequest request) {
        return productGateway.add(
                Product.ProductBuilder.aProduct()
                        .withName(request.getName())
                        .withCategory(request.getCategory())
                        .withPrice(new Money(request.getPrice()))
                        .withDescription(request.getDescription())
                        .withImages(request.getImages().stream().map(Image::new).toList())
                        .build()).getValue();
    }
}
