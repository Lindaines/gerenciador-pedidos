package com.lachonete.gerenciadorpedidos.adapters.out.repository.product;

import com.lachonete.gerenciadorpedidos.adapters.out.repository.mapper.ProductEntityMapper;
import com.lachonete.gerenciadorpedidos.application.core.domain.entity.Product;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.ProductCategory;
import com.lachonete.gerenciadorpedidos.application.ports.out.product.ListProductsByCategoryOutputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListProductsByCategoryAdapter implements ListProductsByCategoryOutputPort {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductEntityMapper productEntityMapper;

    @Override
    public List<Product> find(ProductCategory category) {
        var productEntities =  productRepository.findByCategory(category);
        return productEntities.stream().map(productEntity -> productEntityMapper.toProduct(productEntity)).toList();
    }

}
