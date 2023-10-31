package com.lachonete.gerenciadorpedidos.application.ports.out.product;

import com.lachonete.gerenciadorpedidos.application.core.domain.entity.Product;

import java.util.Optional;

public interface FindProductOutputPort {
    Optional<Product> find(Product product);
}
