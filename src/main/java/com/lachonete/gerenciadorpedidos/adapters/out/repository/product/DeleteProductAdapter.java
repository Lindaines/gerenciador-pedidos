package com.lachonete.gerenciadorpedidos.adapters.out.repository.product;

import com.lachonete.gerenciadorpedidos.adapters.out.repository.mapper.ProductEntityMapper;
import com.lachonete.gerenciadorpedidos.application.core.domain.entity.Product;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.ProductId;
import com.lachonete.gerenciadorpedidos.application.ports.out.product.DeleteProductOutputPort;
import com.lachonete.gerenciadorpedidos.application.ports.out.product.UpdateProductOutputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeleteProductAdapter implements DeleteProductOutputPort {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductEntityMapper productEntityMapper;

    @Override
    public void delete(ProductId productId) {
        productRepository.deleteById(productId.getValue());
    }
}
