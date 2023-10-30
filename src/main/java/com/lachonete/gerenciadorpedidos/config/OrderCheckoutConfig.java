package com.lachonete.gerenciadorpedidos.config;

import com.lachonete.gerenciadorpedidos.adapters.out.repository.CreateProductAdapter;
import com.lachonete.gerenciadorpedidos.adapters.out.repository.OrderCheckoutAdapter;
import com.lachonete.gerenciadorpedidos.application.core.usecase.order.OrderCheckoutUseCase;
import com.lachonete.gerenciadorpedidos.application.core.usecase.product.CreateProductUseCase;
import com.lachonete.gerenciadorpedidos.application.ports.out.order.OrderCheckoutOutputPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderCheckoutConfig {
    @Bean
    public OrderCheckoutUseCase orderCheckoutUseCase(OrderCheckoutAdapter orderCheckoutAdapter){
        return new OrderCheckoutUseCase(orderCheckoutAdapter);
    }
}
