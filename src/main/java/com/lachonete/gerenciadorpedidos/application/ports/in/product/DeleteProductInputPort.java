package com.lachonete.gerenciadorpedidos.application.ports.in.product;

import com.lachonete.gerenciadorpedidos.application.core.domain.entity.Product;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.ProductId;

public interface DeleteProductInputPort {
    void delete(ProductId productId);
}
