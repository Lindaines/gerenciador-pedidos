package com.lachonete.gerenciadorpedidos.application.ports.in.order;

import com.lachonete.gerenciadorpedidos.application.core.domain.entity.Order;
import com.lachonete.gerenciadorpedidos.application.core.domain.entity.OrderItem;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.OrderId;

import java.util.List;

public interface ListQueuedOrdersInputPort {
    List<Order> getOrders();
}
