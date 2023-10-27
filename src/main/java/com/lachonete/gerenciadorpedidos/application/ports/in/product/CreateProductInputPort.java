package com.lachonete.gerenciadorpedidos.application.ports.in.product;

import com.lachonete.gerenciadorpedidos.application.core.domain.entity.Product;

public interface CreateProductInputPort {
    void create(Product product);
}
