package com.lachonete.gerenciadorpedidos.application.core.usecase.order;

import com.lachonete.gerenciadorpedidos.application.core.domain.entity.Order;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.OrderId;
import com.lachonete.gerenciadorpedidos.application.ports.in.order.OrderCheckoutInputPort;
import com.lachonete.gerenciadorpedidos.application.ports.out.order.OrderCheckoutOutputPort;


public class OrderCheckoutUseCase implements OrderCheckoutInputPort {
    private final OrderCheckoutOutputPort orderCheckoutOutputPort;

    public OrderCheckoutUseCase(OrderCheckoutOutputPort checkoutOutputPort) {
        this.orderCheckoutOutputPort = checkoutOutputPort;
    }


    @Override
    public OrderId checkout(Order order) {
        return orderCheckoutOutputPort.checkout(order);
    }
}
