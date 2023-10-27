package com.lachonete.gerenciadorpedidos.application.ports.out.product;

import com.lachonete.gerenciadorpedidos.application.core.domain.entity.Product;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.ProductCategory;

import java.util.List;

public interface ListProductsByCategoryOutputPort {
    List<Product> find(ProductCategory category);
}
