package com.lachonete.gerenciadorpedidos.config;

import com.lachonete.gerenciadorpedidos.adapters.out.repository.customer.CreateCustomerAdapter;
import com.lachonete.gerenciadorpedidos.application.core.usecase.customer.CreateCustomerUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InsertCustomerConfig {
    @Bean
    public CreateCustomerUseCase createCustomerUseCase(CreateCustomerAdapter createCustomerAdapter){
        return new CreateCustomerUseCase(createCustomerAdapter);
    }
}
