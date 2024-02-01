package com.lachonete.gerenciadorpedidos.usecases.order.update;


import com.lachonete.gerenciadorpedidos.entities.Order;
import com.lachonete.gerenciadorpedidos.ports.database.OrderGateway;
import com.lachonete.gerenciadorpedidos.ports.usescases.order.update.UpdateOrderInputBoundary;
import com.lachonete.gerenciadorpedidos.ports.usescases.order.update.UpdateOrderRequest;

import java.util.UUID;

public class UpdateOrder implements UpdateOrderInputBoundary {

    private final OrderGateway orderGateway;

    public UpdateOrder(OrderGateway orderGateway) {
        this.orderGateway = orderGateway;
    }

    @Override
    public void execute(UpdateOrderRequest request) {
        validateOrder(request.getId());
        partialUpdate(request);
    }
    public void validateOrder(UUID id){
        Order order = orderGateway.getById(id);
        if (order==null){
            throw new OrderGateway.OrderNotFoundException();
        }
        // to do - implement more validations
    }

    private void partialUpdate(UpdateOrderRequest request) {
        orderGateway.updateStatus(request.getId(), request.getStatus());
    }
}
