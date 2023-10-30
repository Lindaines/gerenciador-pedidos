package com.lachonete.gerenciadorpedidos.adapters.in.controller.mapper;

import com.lachonete.gerenciadorpedidos.adapters.in.controller.request.OrderItemRequest;
import com.lachonete.gerenciadorpedidos.adapters.in.controller.request.OrderRequest;
import com.lachonete.gerenciadorpedidos.application.core.domain.entity.Order;
import com.lachonete.gerenciadorpedidos.application.core.domain.entity.OrderItem;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.ProductId;
import org.springframework.stereotype.Component;

@Component
public class OrderItemMapper {
    public OrderItem toOrderItem(OrderItemRequest orderItemRequest) {
        return OrderItem.OrderItemBuilder.anOrderItem()
                .withProductId(new ProductId(orderItemRequest.getProductId()))
                .withQuantity(orderItemRequest.getQuantity())
                .build();
    }
}
