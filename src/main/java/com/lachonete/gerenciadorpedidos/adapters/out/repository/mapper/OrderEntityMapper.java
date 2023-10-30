package com.lachonete.gerenciadorpedidos.adapters.out.repository.mapper;

import com.lachonete.gerenciadorpedidos.adapters.out.repository.entity.OrderEntity;
import com.lachonete.gerenciadorpedidos.adapters.out.repository.entity.ProductEntity;
import com.lachonete.gerenciadorpedidos.application.core.domain.entity.Order;
import com.lachonete.gerenciadorpedidos.application.core.domain.entity.Product;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.Image;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.Money;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.ProductId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class OrderEntityMapper {

    @Autowired
    OrderItemEntityMapper orderItemEntityMapper;
    public OrderEntity toOrderEntity(Order order) {
        var orderItemsEntity = order.getItems().stream().map(orderItem -> orderItemEntityMapper.toOrderItemEntity(orderItem)).toList();

        var orderEntity = OrderEntity.builder()
                .orderStatus(order.getOrderStatus())
                .price(BigDecimal.valueOf(10))
                .items(orderItemsEntity)
                .pickUpCode(123123L)
                . build();

        orderEntity.getItems().forEach(orderItemEntity -> orderItemEntity.setOrder(orderEntity));
        return orderEntity;
    }
}
