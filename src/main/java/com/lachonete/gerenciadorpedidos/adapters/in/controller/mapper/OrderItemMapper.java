package com.lachonete.gerenciadorpedidos.adapters.in.controller.mapper;

import com.lachonete.gerenciadorpedidos.adapters.in.controller.request.OrderItemRequest;
import com.lachonete.gerenciadorpedidos.application.core.domain.entity.OrderItem;
import com.lachonete.gerenciadorpedidos.application.core.domain.entity.Product;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.Money;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.ProductId;
import org.springframework.stereotype.Component;

@Component
public class OrderItemMapper {
    public OrderItem toOrderItem(OrderItemRequest orderItemRequest) {
        var product = new Product(new ProductId(orderItemRequest.getProductId()));
        var price = new Money(orderItemRequest.getPrice());
        var subTotal = new Money(orderItemRequest.getSubTotal());
        return OrderItem.OrderItemBuilder.anOrderItem()
                .withProduct(product)
                .withQuantity(orderItemRequest.getQuantity())
                .withSubTotal(subTotal)
                .withPrice(price)
                .build();
    }
}
