package com.lachonete.gerenciadorpedidos.application.ports.in.product;

import com.lachonete.gerenciadorpedidos.application.core.domain.entity.Product;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.ProductCategory;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.ProductId;

import java.util.List;

public interface FindProductByIdInputPort {
    Product findById(ProductId productId);
}
