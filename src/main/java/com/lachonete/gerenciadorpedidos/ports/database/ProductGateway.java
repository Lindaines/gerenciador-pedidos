package com.lachonete.gerenciadorpedidos.ports.database;


import com.lachonete.gerenciadorpedidos.entities.Product;
import com.lachonete.gerenciadorpedidos.entities.valueobject.ProductId;

import java.util.Collection;

public interface ProductGateway {
    Collection<Product> getAll();
    ProductId add(Product product);
    Product getById(ProductId id);
    void update(Product product);

    public interface BadRequest {}
    public interface NotFound {}

    public class ProductAlreadyByNameExistsException extends RuntimeException implements BadRequest {
    }

    public class ProductNotFoundException extends RuntimeException implements NotFound {
    }

}
