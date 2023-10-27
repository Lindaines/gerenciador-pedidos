package com.lachonete.gerenciadorpedidos.application.core.usecase.product;

import com.lachonete.gerenciadorpedidos.application.core.domain.entity.Product;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.ProductCategory;
import com.lachonete.gerenciadorpedidos.application.ports.in.product.ListProductsByCategoryInputPort;
import com.lachonete.gerenciadorpedidos.application.ports.out.product.ListProductsByCategoryOutputPort;

import java.util.List;


public class ListProductsByCategoryUseCase implements ListProductsByCategoryInputPort {
    private final ListProductsByCategoryOutputPort listProductsByCategoryOutputPort;

    public ListProductsByCategoryUseCase(ListProductsByCategoryOutputPort listProductsByCategoryOutputPort) {
        this.listProductsByCategoryOutputPort = listProductsByCategoryOutputPort;
    }

    @Override
    public List<Product> find(ProductCategory category) {
        return listProductsByCategoryOutputPort.find(category);
    }

}
