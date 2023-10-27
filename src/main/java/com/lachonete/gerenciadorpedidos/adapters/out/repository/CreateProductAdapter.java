package com.lachonete.gerenciadorpedidos.adapters.out.repository;

import com.lachonete.gerenciadorpedidos.adapters.out.repository.mapper.CustomerEntityMapper;
import com.lachonete.gerenciadorpedidos.adapters.out.repository.mapper.ProductEntityMapper;
import com.lachonete.gerenciadorpedidos.application.core.domain.entity.Customer;
import com.lachonete.gerenciadorpedidos.application.core.domain.entity.Product;
import com.lachonete.gerenciadorpedidos.application.ports.out.customer.CreateCustomerOutputPort;
import com.lachonete.gerenciadorpedidos.application.ports.out.product.CreateProductOutputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateProductAdapter implements CreateProductOutputPort {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductEntityMapper productEntityMapper;

    @Override
    public void create(Product product) {
        var productEntity = productEntityMapper.toProductEntity(product);
        productRepository.save(productEntity);
    }
}
