package com.lachonete.gerenciadorpedidos.adapters.out.repository.order;

import com.lachonete.gerenciadorpedidos.adapters.out.repository.entity.OrderEntity;
import com.lachonete.gerenciadorpedidos.adapters.out.repository.mapper.OrderEntityMapper;
import com.lachonete.gerenciadorpedidos.application.core.domain.entity.Order;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.OrderStatus;
import com.lachonete.gerenciadorpedidos.application.ports.out.order.ListQueuedOrdersOutputPort;
import com.lachonete.gerenciadorpedidos.application.ports.out.order.OrderCheckoutOutputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListOrderQueuedAdapter implements ListQueuedOrdersOutputPort {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderEntityMapper orderEntityMapper;

    @Override
    public List<Order> getOrders() {
        List<OrderEntity> orderEntities = orderRepository.findByOrderStatus(OrderStatus.CRIADO);
        return orderEntities.stream().map(orderEntity -> orderEntityMapper.toOrder(orderEntity)).toList();
    }

}
