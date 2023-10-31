package com.lachonete.gerenciadorpedidos.adapters.out.repository.mapper;

import com.lachonete.gerenciadorpedidos.adapters.out.repository.entity.OrderItemEntity;
import com.lachonete.gerenciadorpedidos.application.core.domain.entity.OrderItem;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.ProductId;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class OrderItemEntityMapper {
    public OrderItemEntity toOrderItemEntity(OrderItem orderItem) {
        return OrderItemEntity.builder()
                .productId(orderItem.getProduct().getId().getValue())
                .quantity(orderItem.getQuantity())
                .subTotal(BigDecimal.valueOf(10))
                .build();
    }

    public OrderItem toOrderItem(OrderItemEntity orderItemEntity) {
        return OrderItem.OrderItemBuilder.anOrderItem()
                .build();
    }
}
