package com.lachonete.gerenciadorpedidos.adapters.in.controller.mapper;

import com.lachonete.gerenciadorpedidos.adapters.in.controller.request.OrderItemRequest;
import com.lachonete.gerenciadorpedidos.adapters.in.controller.request.OrderRequest;
import com.lachonete.gerenciadorpedidos.application.core.domain.entity.Order;
import com.lachonete.gerenciadorpedidos.application.core.domain.entity.OrderItem;
import com.lachonete.gerenciadorpedidos.application.core.domain.entity.Product;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.Money;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.ProductId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {

    @Autowired
    OrderItemMapper orderItemMapper;
    public Order toOrder(OrderRequest orderRequest) {
        var orderItems = orderRequest.getItems().stream().map(orderItemRequest -> orderItemMapper.toOrderItem(orderItemRequest)).toList();
        return Order.OrderBuilder.anOrder().withItems(orderItems).build();
    }
}
