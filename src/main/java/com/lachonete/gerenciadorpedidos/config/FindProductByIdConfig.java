package com.lachonete.gerenciadorpedidos.config;

import com.lachonete.gerenciadorpedidos.adapters.out.repository.product.FindProductAdapter;
import com.lachonete.gerenciadorpedidos.adapters.out.repository.product.ListProductsByCategoryAdapter;
import com.lachonete.gerenciadorpedidos.application.core.usecase.product.FindProductByIdUseCase;
import com.lachonete.gerenciadorpedidos.application.core.usecase.product.ListProductsByCategoryUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FindProductByIdConfig {
    @Bean
    public FindProductByIdUseCase findProductByIdUseCase(FindProductAdapter findProductAdapter){
        return new FindProductByIdUseCase(findProductAdapter);
    }
}
