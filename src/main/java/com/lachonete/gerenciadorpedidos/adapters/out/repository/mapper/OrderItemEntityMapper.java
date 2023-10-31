package com.lachonete.gerenciadorpedidos.adapters.out.repository.mapper;

import com.lachonete.gerenciadorpedidos.adapters.out.repository.entity.OrderItemEntity;
import com.lachonete.gerenciadorpedidos.adapters.out.repository.entity.ProductEntity;
import com.lachonete.gerenciadorpedidos.application.core.domain.entity.OrderItem;
import com.lachonete.gerenciadorpedidos.application.core.domain.entity.Product;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.Money;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.ProductId;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class OrderItemEntityMapper {
    public OrderItemEntity toOrderItemEntity(OrderItem orderItem) {
        var productId = orderItem.getProduct().getId().getValue();
        return OrderItemEntity.builder()
                .productId(productId)
                .quantity(orderItem.getQuantity())
                .subTotal(orderItem.getSubTotal().getAmount())
                .price(orderItem.getPrice().getAmount())
                .build();
    }

    public OrderItem toOrderItem(OrderItemEntity orderItemEntity) {
        var productId = new ProductId(orderItemEntity.getProductId());
        var product = new Product(productId);
        var price = new Money(orderItemEntity.getPrice());
        var subtotal = new Money(orderItemEntity.getSubTotal());
        return OrderItem.OrderItemBuilder.anOrderItem()
                .withProduct(product)
                .withQuantity(orderItemEntity.getQuantity())
                .withPrice(price)
                .withSubTotal(subtotal)
                .build();
    }
}
