package com.lachonete.gerenciadorpedidos.config;

import com.lachonete.gerenciadorpedidos.adapters.out.repository.order.OrderCheckoutAdapter;
import com.lachonete.gerenciadorpedidos.application.core.usecase.order.OrderCheckoutUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderCheckoutConfig {
    @Bean
    public OrderCheckoutUseCase orderCheckoutUseCase(OrderCheckoutAdapter orderCheckoutAdapter){
        return new OrderCheckoutUseCase(orderCheckoutAdapter);
    }
}
