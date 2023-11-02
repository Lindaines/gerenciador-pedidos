package com.lachonete.gerenciadorpedidos.config;

import com.lachonete.gerenciadorpedidos.adapters.out.repository.product.DeleteProductAdapter;
import com.lachonete.gerenciadorpedidos.adapters.out.repository.product.FindProductAdapter;
import com.lachonete.gerenciadorpedidos.adapters.out.repository.product.UpdateProductAdapter;
import com.lachonete.gerenciadorpedidos.application.core.usecase.product.DeleteProductUseCase;
import com.lachonete.gerenciadorpedidos.application.core.usecase.product.UpdateProductUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DeleteProductConfig {
    @Bean
    public DeleteProductUseCase deleteProductUseCase(DeleteProductAdapter deleteProductAdapter, FindProductAdapter findProductAdapter){
        return new DeleteProductUseCase(deleteProductAdapter, findProductAdapter);
    }
}
