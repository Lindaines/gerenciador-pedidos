package com.lachonete.gerenciadorpedidos.application.ports.in.product;

import com.lachonete.gerenciadorpedidos.application.core.domain.entity.Product;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.ProductCategory;

import java.util.List;

public interface ListProductsByCategoryInputPort {
    List<Product> find(ProductCategory category);
}
