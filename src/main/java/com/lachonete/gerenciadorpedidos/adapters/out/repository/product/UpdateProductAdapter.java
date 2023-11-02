package com.lachonete.gerenciadorpedidos.adapters.out.repository.product;

import com.lachonete.gerenciadorpedidos.adapters.out.repository.mapper.ProductEntityMapper;
import com.lachonete.gerenciadorpedidos.application.core.domain.entity.Product;
import com.lachonete.gerenciadorpedidos.application.ports.out.product.CreateProductOutputPort;
import com.lachonete.gerenciadorpedidos.application.ports.out.product.UpdateProductOutputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateProductAdapter implements UpdateProductOutputPort {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductEntityMapper productEntityMapper;

    @Override
    public void update(Product product) {
        var productEntity = productEntityMapper.toProductEntity(product);
        productEntity.setId(product.getId().getValue());
        productRepository.save(productEntity);
    }
}
