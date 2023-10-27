package com.lachonete.gerenciadorpedidos.application.ports.out.product;

import com.lachonete.gerenciadorpedidos.application.core.domain.entity.Product;

public interface CreateProductOutputPort {
    void create (Product product);
}
