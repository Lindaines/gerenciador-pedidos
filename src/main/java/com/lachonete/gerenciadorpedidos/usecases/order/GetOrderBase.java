package com.lachonete.gerenciadorpedidos.usecases.order;


import com.lachonete.gerenciadorpedidos.entities.Order;
import com.lachonete.gerenciadorpedidos.entities.OrderItem;
import com.lachonete.gerenciadorpedidos.entities.Product;
import com.lachonete.gerenciadorpedidos.entities.valueobject.Image;
import com.lachonete.gerenciadorpedidos.ports.usescases.order.get.OrderItemResponse;
import com.lachonete.gerenciadorpedidos.ports.usescases.order.get.OrderResponse;
import com.lachonete.gerenciadorpedidos.ports.usescases.order.get.OrdersResponse;
import com.lachonete.gerenciadorpedidos.ports.usescases.product.get.ProductResponse;

public abstract class GetOrderBase {
    protected GetOrderBase() { }

    public static OrderItemResponse makeOrderItemResponse (OrderItem orderItem){
        return OrderItemResponse.builder()
                .subTotal(orderItem.getSubTotal().getAmount())
                .quantity(orderItem.getQuantity())
                .productId(orderItem.getProduct().getId().getValue())
                .id(orderItem.getId().getValue())
                .build();
    }

    public static OrderResponse makeOrderResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId().getValue())
                .pickUpCode(order.getPickupCode())
                .orderStatus(order.getOrderStatus())
                .price(order.getPrice().getAmount())
                .items(order.getItems().stream().map(GetOrderBase::makeOrderItemResponse).toList())
                .build();
    }

}