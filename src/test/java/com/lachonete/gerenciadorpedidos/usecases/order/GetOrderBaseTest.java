package com.lachonete.gerenciadorpedidos.usecases.order;

import com.lachonete.gerenciadorpedidos.entities.Order;
import com.lachonete.gerenciadorpedidos.entities.OrderItem;
import com.lachonete.gerenciadorpedidos.entities.Product;
import com.lachonete.gerenciadorpedidos.entities.valueobject.*;
import com.lachonete.gerenciadorpedidos.ports.usescases.order.get.OrderItemResponse;
import com.lachonete.gerenciadorpedidos.ports.usescases.order.get.OrderResponse;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class GetOrderBaseTest {

    @Test
    void makeOrderItemResponse_shouldConvertOrderItemToResponseCorrectly() {


        // Given
        Product product = Product.ProductBuilder.aProduct()
                .withId(new ProductId(UUID.randomUUID()))
                .withName("Burger")
                .withPrice(new Money(new BigDecimal("10.50")))
                .build();

        OrderItem orderItem = OrderItem.OrderItemBuilder.anOrderItem()
                .withId(new OrderItemId(1L))
                .withProduct(product)
                .withQuantity(2)
                .withPrice(new Money(new BigDecimal("21.00")))
                .withSubTotal(new Money(new BigDecimal("21.00")))
                .build();


        // When
        OrderItemResponse response = GetOrderBase.makeOrderItemResponse(orderItem);

        // Then
        assertNotNull(response);
        assertEquals(orderItem.getId().getValue(), response.getId());
        assertEquals(orderItem.getProduct().getId().getValue(), response.getProductId());
        assertEquals(orderItem.getQuantity(), response.getQuantity());
        assertEquals(orderItem.getSubTotal().getAmount(), response.getSubTotal());
    }


    @Test
    void makeOrderResponse_shouldConvertOrderToResponseCorrectly() {
        // Given
        Product product1 = Product.ProductBuilder.aProduct()
                .withId(new ProductId(UUID.randomUUID()))
                .withName("Burger")
                .withPrice(new Money(new BigDecimal("10.50")))
                .build();

        Product product2 = Product.ProductBuilder.aProduct()
                .withId(new ProductId(UUID.randomUUID()))
                .withName("Fies")
                .withPrice(new Money(new BigDecimal("5.00")))
                .build();

        OrderItem orderItem1 = OrderItem.OrderItemBuilder.anOrderItem()
                .withId(new OrderItemId(1L))
                .withProduct(product1)
                .withQuantity(2)
                .withPrice(new Money(new BigDecimal("21.00")))
                .withSubTotal(new Money(new BigDecimal("21.00")))
                .build();

        OrderItem orderItem2 = OrderItem.OrderItemBuilder.anOrderItem()
                .withId(new OrderItemId(1L))
                .withProduct(product1)
                .withQuantity(1)
                .withPrice(new Money(new BigDecimal("5.00")))
                .withSubTotal(new Money(new BigDecimal("5.00")))
                .build();

        Order order = Order.OrderBuilder.anOrder()
                .withId(new OrderId(UUID.randomUUID()))
                .withPickupCode(1001)
                .withOrderStatus(OrderStatus.CRIADO)
                .withPrice(new Money(new BigDecimal("26.00")))
                .withItems(List.of(orderItem1, orderItem2))
                .build();


        // When
        OrderResponse response = GetOrderBase.makeOrderResponse(order);

        // Then
        assertNotNull(response);
        assertEquals(order.getId().getValue(), response.getId());
        assertEquals(order.getPickupCode(), response.getPickUpCode());
        assertEquals(order.getOrderStatus(), response.getOrderStatus());
        assertEquals(order.getPrice().getAmount(), response.getPrice());
        assertEquals(2, response.getItems().size());

        // Verify first item
        OrderItemResponse firstItemResponse = response.getItems().get(0);
        assertEquals(orderItem1.getId().getValue(), firstItemResponse.getId());
        assertEquals(orderItem1.getProduct().getId().getValue(), firstItemResponse.getProductId());
        assertEquals(orderItem1.getQuantity(), firstItemResponse.getQuantity());
        assertEquals(orderItem1.getSubTotal().getAmount(), firstItemResponse.getSubTotal());

        // Verify second item
        OrderItemResponse secondItemResponse = response.getItems().get(1);
        assertEquals(orderItem2.getId().getValue(), secondItemResponse.getId());
        assertEquals(orderItem2.getProduct().getId().getValue(), secondItemResponse.getProductId());
        assertEquals(orderItem2.getQuantity(), secondItemResponse.getQuantity());
        assertEquals(orderItem2.getSubTotal().getAmount(), secondItemResponse.getSubTotal());
    }


    @Test
    void makeOrderResponse_withEmptyItems_shouldReturnEmptyList() {
        // Given
        Order order = Order.OrderBuilder.anOrder()
                .withId(new OrderId(UUID.randomUUID()))
                .withPickupCode(1001)
                .withOrderStatus(OrderStatus.CRIADO)
                .withPrice(new Money(new BigDecimal("26.00")))
                .withItems(List.of())
                .build();

        // When
        OrderResponse response = GetOrderBase.makeOrderResponse(order);

        // Then
        assertNotNull(response);
        assertTrue(response.getItems().isEmpty());
    }

    @Test
    void makeOrderItemResponse_withNullInput_shouldThrowException() {
        assertThrows(NullPointerException.class, () -> GetOrderBase.makeOrderItemResponse(null));
    }

    @Test
    void makeOrderResponse_withNullInput_shouldThrowException() {
        assertThrows(NullPointerException.class, () -> GetOrderBase.makeOrderResponse(null));
    }
}