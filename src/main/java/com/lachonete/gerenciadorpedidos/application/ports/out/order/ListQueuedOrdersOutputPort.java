package com.lachonete.gerenciadorpedidos.application.ports.out.order;

import com.lachonete.gerenciadorpedidos.application.core.domain.entity.Order;

import java.util.List;
import java.util.Optional;

public interface ListQueuedOrdersOutputPort {
    List<Order> getOrders();
}
