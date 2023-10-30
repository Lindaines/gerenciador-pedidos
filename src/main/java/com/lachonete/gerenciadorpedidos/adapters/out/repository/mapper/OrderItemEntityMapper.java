package com.lachonete.gerenciadorpedidos.adapters.out.repository.mapper;

import com.lachonete.gerenciadorpedidos.adapters.out.repository.entity.OrderEntity;
import com.lachonete.gerenciadorpedidos.adapters.out.repository.entity.OrderItemEntity;
import com.lachonete.gerenciadorpedidos.application.core.domain.entity.Order;
import com.lachonete.gerenciadorpedidos.application.core.domain.entity.OrderItem;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class OrderItemEntityMapper {
    public OrderItemEntity toOrderItemEntity(OrderItem orderItem) {
        return OrderItemEntity.builder()
                .productId(orderItem.getProductId().getValue())
                .quantity(orderItem.getQuantity())
                .subTotal(BigDecimal.valueOf(10))
                .build();
    }
}
