package com.lachonete.gerenciadorpedidos.config;

import com.lachonete.gerenciadorpedidos.adapters.out.repository.CreateCustomerAdapter;
import com.lachonete.gerenciadorpedidos.adapters.out.repository.CreateProductAdapter;
import com.lachonete.gerenciadorpedidos.application.core.usecase.customer.CreateCustomerUseCase;
import com.lachonete.gerenciadorpedidos.application.core.usecase.product.CreateProductUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InsertProductConfig {
    @Bean
    public CreateProductUseCase createProductUseCase(CreateProductAdapter createProductAdapter){
        return new CreateProductUseCase(createProductAdapter);
    }
}
