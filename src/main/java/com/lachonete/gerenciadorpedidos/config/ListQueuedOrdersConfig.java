package com.lachonete.gerenciadorpedidos.config;

import com.lachonete.gerenciadorpedidos.adapters.out.repository.order.ListOrderQueuedAdapter;
import com.lachonete.gerenciadorpedidos.adapters.out.repository.order.OrderCheckoutAdapter;
import com.lachonete.gerenciadorpedidos.adapters.out.repository.product.FindProductAdapter;
import com.lachonete.gerenciadorpedidos.application.core.usecase.order.ListQueuedOrdersUseCase;
import com.lachonete.gerenciadorpedidos.application.core.usecase.order.OrderCheckoutUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ListQueuedOrdersConfig {
    @Bean
    public ListQueuedOrdersUseCase listQueuedOrdersUseCase(ListOrderQueuedAdapter listOrderQueuedAdapter){
        return new ListQueuedOrdersUseCase(listOrderQueuedAdapter);
    }
}
