package com.lachonete.gerenciadorpedidos.config;

import com.lachonete.gerenciadorpedidos.adapters.out.repository.order.OrderCheckoutAdapter;
import com.lachonete.gerenciadorpedidos.adapters.out.repository.product.FindProductAdapter;
import com.lachonete.gerenciadorpedidos.application.core.usecase.order.OrderCheckoutUseCase;
import com.lachonete.gerenciadorpedidos.application.ports.out.product.FindProductOutputPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderCheckoutConfig {
    @Bean
    public OrderCheckoutUseCase orderCheckoutUseCase(OrderCheckoutAdapter orderCheckoutAdapter, FindProductAdapter findProductAdapter){
        return new OrderCheckoutUseCase(orderCheckoutAdapter, findProductAdapter);
    }
}
