package com.lachonete.gerenciadorpedidos.config;

import com.lachonete.gerenciadorpedidos.adapters.out.repository.CreateCustomerAdapter;
import com.lachonete.gerenciadorpedidos.adapters.out.repository.IdentifyCustomerAdapter;
import com.lachonete.gerenciadorpedidos.application.core.usecase.CreateCustomerUseCase;
import com.lachonete.gerenciadorpedidos.application.core.usecase.IdentifyCustomerUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IdentifyCustomerConfig {
    @Bean
    public IdentifyCustomerUseCase identifyCustomerUseCase(IdentifyCustomerAdapter identifyCustomerAdapter){
        return new IdentifyCustomerUseCase(identifyCustomerAdapter);
    }
}
