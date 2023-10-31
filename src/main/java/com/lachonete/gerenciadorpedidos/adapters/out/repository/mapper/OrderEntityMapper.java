package com.lachonete.gerenciadorpedidos.adapters.out.repository.mapper;

import com.lachonete.gerenciadorpedidos.adapters.out.repository.entity.OrderEntity;
import com.lachonete.gerenciadorpedidos.application.core.domain.entity.Order;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.Money;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.OrderId;
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
                .price(order.getPrice().getAmount())
                .items(orderItemsEntity)
                //.pickUpCode(123123L)
                . build();

        orderEntity.getItems().forEach(orderItemEntity -> orderItemEntity.setOrder(orderEntity));
        return orderEntity;
    }


    public Order toOrder(OrderEntity orderEntity) {
        var orderItems = orderEntity.getItems().stream().map(orderItemEntity -> orderItemEntityMapper.toOrderItem(orderItemEntity)).toList();
        var id = new OrderId(orderEntity.getId());
        var order = Order.OrderBuilder.anOrder()
                .withId(id)
                .withOrderStatus(orderEntity.getOrderStatus())
                .withPrice(new Money(orderEntity.getPrice()))
                .withItems(orderItems)
                //.pickUpCode(123123L)
                . build();

        orderEntity.getItems().forEach(orderItemEntity -> orderItemEntity.setOrder(orderEntity));
        return order;
    }
}
