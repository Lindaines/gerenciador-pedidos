package com.lachonete.gerenciadorpedidos.application.core.usecase.order;

import com.lachonete.gerenciadorpedidos.application.core.domain.entity.Order;
import com.lachonete.gerenciadorpedidos.application.core.domain.entity.OrderItem;
import com.lachonete.gerenciadorpedidos.application.core.domain.entity.Product;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.OrderId;
import com.lachonete.gerenciadorpedidos.application.ports.in.order.ListQueuedOrdersInputPort;
import com.lachonete.gerenciadorpedidos.application.ports.out.order.ListQueuedOrdersOutputPort;
import com.lachonete.gerenciadorpedidos.application.ports.out.order.OrderCheckoutOutputPort;
import com.lachonete.gerenciadorpedidos.application.ports.out.product.FindProductOutputPort;

import java.util.List;
import java.util.Optional;


public class ListQueuedOrdersUseCase implements ListQueuedOrdersInputPort {
    private final ListQueuedOrdersOutputPort listQueuedOrdersOutputPort;

    public ListQueuedOrdersUseCase(ListQueuedOrdersOutputPort listQueuedOrdersOutputPort) {
        this.listQueuedOrdersOutputPort = listQueuedOrdersOutputPort;
    }

    @Override
    public List<Order> getOrders() {
        return listQueuedOrdersOutputPort.getOrders();
    }

}
