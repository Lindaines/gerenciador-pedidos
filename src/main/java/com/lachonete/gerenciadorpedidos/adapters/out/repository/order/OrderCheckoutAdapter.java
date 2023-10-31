package com.lachonete.gerenciadorpedidos.adapters.out.repository.order;

import com.lachonete.gerenciadorpedidos.adapters.out.repository.entity.OrderEntity;
import com.lachonete.gerenciadorpedidos.adapters.out.repository.mapper.OrderEntityMapper;
import com.lachonete.gerenciadorpedidos.application.core.domain.entity.Order;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.OrderId;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.OrderStatus;
import com.lachonete.gerenciadorpedidos.application.ports.out.order.OrderCheckoutOutputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class OrderCheckoutAdapter implements OrderCheckoutOutputPort {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private OrderEntityMapper orderEntityMapper;


    @Override
    public Order persist(Order order) {
        var orderEntity = orderEntityMapper.toOrderEntity(order);
        orderEntity.setOrderStatus(OrderStatus.CRIADO);
        OrderEntity orderEntityCreated = orderRepository.save(orderEntity);
        return orderEntityMapper.toOrder(orderEntityCreated);
    }
}
