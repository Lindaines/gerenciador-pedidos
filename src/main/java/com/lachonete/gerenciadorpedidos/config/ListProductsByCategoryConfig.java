package com.lachonete.gerenciadorpedidos.config;

import com.lachonete.gerenciadorpedidos.adapters.out.repository.CreateProductAdapter;
import com.lachonete.gerenciadorpedidos.adapters.out.repository.ListProductsByCategoryAdapter;
import com.lachonete.gerenciadorpedidos.application.core.usecase.product.CreateProductUseCase;
import com.lachonete.gerenciadorpedidos.application.core.usecase.product.ListProductsByCategoryUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ListProductsByCategoryConfig {
    @Bean
    public ListProductsByCategoryUseCase listProductsByCategoryUseCase(ListProductsByCategoryAdapter listProductsByCategoryAdapter){
        return new ListProductsByCategoryUseCase(listProductsByCategoryAdapter);
    }
}
