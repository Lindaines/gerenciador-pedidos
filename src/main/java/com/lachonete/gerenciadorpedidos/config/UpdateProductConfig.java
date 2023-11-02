package com.lachonete.gerenciadorpedidos.config;

import com.lachonete.gerenciadorpedidos.adapters.out.repository.product.CreateProductAdapter;
import com.lachonete.gerenciadorpedidos.adapters.out.repository.product.FindProductAdapter;
import com.lachonete.gerenciadorpedidos.adapters.out.repository.product.UpdateProductAdapter;
import com.lachonete.gerenciadorpedidos.application.core.usecase.product.CreateProductUseCase;
import com.lachonete.gerenciadorpedidos.application.core.usecase.product.UpdateProductUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UpdateProductConfig {
    @Bean
    public UpdateProductUseCase updateProductUseCase(UpdateProductAdapter updateProductAdapter, FindProductAdapter findProductAdapter){
        return new UpdateProductUseCase(updateProductAdapter, findProductAdapter);
    }
}
