package com.lachonete.gerenciadorpedidos.entities;

import com.lachonete.gerenciadorpedidos.entities.valueobject.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class OrderTest {

    private final OrderId testOrderId = new OrderId(UUID.randomUUID());
    private final Money testPrice = new Money(new BigDecimal("50.00"));
    private final List<OrderItem> testItems = List.of(
            createOrderItem(new BigDecimal("20.00")),
            createOrderItem(new BigDecimal("30.00"))
    );

    @Test
    void shouldInitializeOrderWithCorrectStatus() {
        Order order = new Order();
        order.initializeOrder();

        assertEquals(OrderStatus.CRIADO, order.getOrderStatus());
    }

    @Test
    void shouldCalculateTotalPriceCorrectly() {
        Order order = Order.OrderBuilder.anOrder()
                .withItems(testItems)
                .build();

        order.setPriceInfo(order);

        assertEquals(new BigDecimal("50.00"), order.getPrice().getAmount());
    }

    @Test
    void shouldBuildOrderWithAllFields() {
        Order order = Order.OrderBuilder.anOrder()
                .withId(testOrderId)
                .withPrice(testPrice)
                .withPaymentId("PAY123")
                .withItems(testItems)
                .withPickupCode(1001)
                .withOrderStatus(OrderStatus.RECEBIDO)
                .build();

        assertAll(
                () -> assertThat(order.getId()).isEqualTo(testOrderId),
                () -> assertThat(order.getPrice()).isEqualTo(testPrice),
                () -> assertThat(order.getPaymentId()).isEqualTo("PAY123"),
                () -> assertThat(order.getItems()).hasSize(2),
                () -> assertThat(order.getPickupCode()).isEqualTo(1001),
                () -> assertThat(order.getOrderStatus()).isEqualTo(OrderStatus.RECEBIDO)
        );
    }

    @ParameterizedTest
    @EnumSource(OrderStatus.class)
    void shouldUpdateStatusToAllPossibleValues(OrderStatus status) {
        Order order = new Order();
        order.updateStatus(status);

        assertEquals(status, order.getOrderStatus());
    }

    @Test
    void shouldSetPickupCodeCorrectly() {
        Order order = new Order();
        order.setPickUpCodeInfo(order, 1234);

        assertEquals(1234, order.getPickupCode());
    }

    @Test
    void shouldSetPaymentIdCorrectly() {
        Order order = new Order();
        order.setPaymentId("PAY_789");

        assertEquals("PAY_789", order.getPaymentId());
    }

    @Test
    void equals_ShouldFollowAggregateRootRules() {
        Order order1 = Order.OrderBuilder.anOrder().withId(testOrderId).build();
        Order order2 = Order.OrderBuilder.anOrder().withId(testOrderId).build();
        Order order3 = Order.OrderBuilder.anOrder().withId(new OrderId(UUID.randomUUID())).build();

        assertAll(
                () -> assertThat(order1).isEqualTo(order2),
                () -> assertThat(order1).isNotEqualTo(order3),
                () -> assertThat(order1.hashCode()).isEqualTo(order2.hashCode())
        );
    }

    @Test
    void shouldHandleEmptyItemsWhenCalculatingPrice() {
        Order order = Order.OrderBuilder.anOrder()
                .withItems(List.of())
                .build();

        order.setPriceInfo(order);

        assertEquals(BigDecimal.ZERO, order.getPrice().getAmount());
    }

    // Helper method
    private static OrderItem createOrderItem(BigDecimal price) {
        return OrderItem.OrderItemBuilder.anOrderItem()
                .withPrice(new Money(price))
                .withQuantity(1)
                .withSubTotal(new Money(price))
                .build();
    }
}