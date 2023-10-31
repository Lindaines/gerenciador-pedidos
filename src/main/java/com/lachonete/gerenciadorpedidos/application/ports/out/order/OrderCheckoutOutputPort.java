package com.lachonete.gerenciadorpedidos.application.ports.out.order;

import com.lachonete.gerenciadorpedidos.application.core.domain.entity.Order;

public interface OrderCheckoutOutputPort {
    Order persist(Order order);
}
