package com.lachonete.gerenciadorpedidos.application.ports.out.product;

import com.lachonete.gerenciadorpedidos.application.core.domain.entity.Product;

public interface UpdateProductOutputPort {
    void update (Product product);
}
