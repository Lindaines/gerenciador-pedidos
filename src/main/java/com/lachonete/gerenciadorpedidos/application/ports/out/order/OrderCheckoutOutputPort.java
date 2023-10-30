package com.lachonete.gerenciadorpedidos.application.ports.out.order;

import com.lachonete.gerenciadorpedidos.application.core.domain.entity.Order;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.OrderId;

public interface OrderCheckoutOutputPort {
    OrderId checkout (Order order);
}
