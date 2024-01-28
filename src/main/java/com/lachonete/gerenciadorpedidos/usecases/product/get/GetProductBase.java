package com.lachonete.gerenciadorpedidos.usecases.product.get;

import com.lachonete.gerenciadorpedidos.entities.Product;
import com.lachonete.gerenciadorpedidos.entities.valueobject.Image;
import com.lachonete.gerenciadorpedidos.ports.usescases.product.get.ProductResponse;

public abstract class GetProductBase {
    protected GetProductBase() { }

    public static ProductResponse makeProductResponse(Product product) {
        return new ProductResponse(
               product.getId().getValue(),
                product.getName(),
                product.getDescription(),
                product.getPrice().getAmount(),
                product.getCategory(),
                product.getImages().stream().map(Image::toString).toList()
        );
    }

}
