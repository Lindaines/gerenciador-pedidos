package com.lachonete.gerenciadorpedidos.application.ports.out.product;

import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.ProductId;

public interface DeleteProductOutputPort {
    void delete (ProductId productId);
}
