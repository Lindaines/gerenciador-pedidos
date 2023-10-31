package com.lachonete.gerenciadorpedidos.adapters.out.repository.product;

import com.lachonete.gerenciadorpedidos.adapters.out.repository.mapper.ProductEntityMapper;
import com.lachonete.gerenciadorpedidos.application.core.domain.entity.Product;
import com.lachonete.gerenciadorpedidos.application.ports.out.product.FindProductOutputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FindProductAdapter implements FindProductOutputPort {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductEntityMapper productEntityMapper;

    @Override
    public Optional<Product> find(Product product) {
        var productEntity =  productRepository.findById(product.getId().getValue());
        return productEntity.map(entity -> productEntityMapper.toProduct(entity));
    }

}
