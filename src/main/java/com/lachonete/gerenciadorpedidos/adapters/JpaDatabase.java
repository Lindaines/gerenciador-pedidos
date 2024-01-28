package com.lachonete.gerenciadorpedidos.adapters;

import com.lachonete.gerenciadorpedidos.adapters.repositories.ProductRepository;
import com.lachonete.gerenciadorpedidos.ports.database.Database;
import com.lachonete.gerenciadorpedidos.ports.database.ProductGateway;

public class JpaDatabase implements Database {
    private ProductGateway productGateway;

    public JpaDatabase(ProductRepository productRepository) {
        productGateway = new JpaProductGateway(productRepository);
    }

    @Override
    public ProductGateway productGateway() {
        return productGateway;
    }
}
