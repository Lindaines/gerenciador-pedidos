package com.lachonete.gerenciadorpedidos.usecases.product.update;


import com.lachonete.gerenciadorpedidos.entities.Product;
import com.lachonete.gerenciadorpedidos.entities.valueobject.Image;
import com.lachonete.gerenciadorpedidos.entities.valueobject.Money;
import com.lachonete.gerenciadorpedidos.entities.valueobject.ProductId;
import com.lachonete.gerenciadorpedidos.ports.database.ProductGateway;
import com.lachonete.gerenciadorpedidos.ports.usescases.product.add.AddProductRequest;
import com.lachonete.gerenciadorpedidos.ports.usescases.product.udpate.UpdateProductInputBoundary;
import com.lachonete.gerenciadorpedidos.ports.usescases.product.udpate.ProductRequest;

public class UpdateProduct implements UpdateProductInputBoundary {
    private final ProductGateway productGateway;

    public UpdateProduct( ProductGateway productGateway) {
        this.productGateway = productGateway;
    }

    private void validateProduct(final AddProductRequest request) {
        if (productAlreadyExists(request))
            throw new ProductGateway.ProductAlreadyByNameExistsException();
    }

    private boolean productAlreadyExists(final AddProductRequest request) {
        // To do: Validation
        return false;
    }


    @Override
    public void execute(ProductRequest request) {
        productGateway.update(Product.ProductBuilder.aProduct()
                .withName(request.getName())
                .withId(new ProductId(request.getId()))
                .withCategory(request.getCategory())
                .withPrice(new Money(request.getPrice()))
                .withDescription(request.getDescription())
                .withImages(request.getImages().stream().map(Image::new).toList())
                .build());
    }
}
