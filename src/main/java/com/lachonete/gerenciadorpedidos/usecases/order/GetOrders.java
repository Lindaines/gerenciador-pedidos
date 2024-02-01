package com.lachonete.gerenciadorpedidos.usecases.order;



import com.lachonete.gerenciadorpedidos.entities.Order;
import com.lachonete.gerenciadorpedidos.entities.valueobject.OrderStatus;
import com.lachonete.gerenciadorpedidos.ports.database.OrderGateway;
import com.lachonete.gerenciadorpedidos.ports.database.ProductGateway;
import com.lachonete.gerenciadorpedidos.ports.presenters.order.OrdersOutputBoundary;
import com.lachonete.gerenciadorpedidos.ports.presenters.product.ProductsOutputBoundary;
import com.lachonete.gerenciadorpedidos.ports.usescases.order.get.GetOrdersInputBoundary;
import com.lachonete.gerenciadorpedidos.ports.usescases.order.get.OrdersResponse;
import com.lachonete.gerenciadorpedidos.ports.usescases.product.get.GetProductsInputBoundary;
import com.lachonete.gerenciadorpedidos.ports.usescases.product.get.ProductsResponse;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GetOrders extends GetOrderBase implements GetOrdersInputBoundary {
    private final OrdersOutputBoundary presenter;
    private final OrderGateway orderGateway;

    public GetOrders(OrdersOutputBoundary presenter, OrderGateway orderGateway) {
        this.presenter = presenter;
        this.orderGateway = orderGateway;
    }

    public void execute() {
        OrdersResponse.OrdersResponseBuilder result = OrdersResponse.builder();
        List<Order> all = orderGateway.getAllExceptFinished();
        List<OrderStatus> orderStatus = List.of(OrderStatus.PRONTO, OrderStatus.EM_PREPARACAO, OrderStatus.RECEBIDO);
        Comparator<OrderStatus> orderStatusComp = Comparator.comparingInt(orderStatus::indexOf);
        ArrayList<Order> sorted = new ArrayList<>(all.stream().toList());
        sorted.sort(Comparator.comparing(Order::getOrderStatus, orderStatusComp));
        sorted.forEach(order -> result.addOrder(makeOrderResponse(order)));
        presenter.present(result.build());
    }

}