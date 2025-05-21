package com.lachonete.gerenciadorpedidos.usecases.order;

import com.lachonete.gerenciadorpedidos.entities.Order;
import com.lachonete.gerenciadorpedidos.entities.valueobject.*;
import com.lachonete.gerenciadorpedidos.ports.database.OrderGateway;
import com.lachonete.gerenciadorpedidos.ports.presenters.order.OrdersOutputBoundary;
import com.lachonete.gerenciadorpedidos.ports.usescases.order.get.OrdersResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetOrdersTest {

    @Mock
    private OrdersOutputBoundary presenter;

    @Mock
    private OrderGateway orderGateway;

    @Captor
    private ArgumentCaptor<OrdersResponse> ordersResponseCaptor;

    private GetOrders getOrders;

    @BeforeEach
    void setUp() {
        getOrders = new GetOrders(presenter, orderGateway);
    }

    @Test
    void execute_shouldRetrieveAndSortOrdersCorrectly() {
        // Given
        Order readyOrder = Order.OrderBuilder.anOrder()
                .withId(new OrderId(UUID.fromString("d45b6c71-c7d7-4a04-bf92-190df9612ff1")))
                .withPickupCode(1001)
                .withOrderStatus(OrderStatus.PRONTO)
                .withPrice(new Money(new BigDecimal("26.00")))
                .withItems(List.of())
                .build();
        Order preparingOrder = Order.OrderBuilder.anOrder()
                .withId(new OrderId(UUID.fromString("d45b6c71-c7d7-4a04-bf92-190df9612ff2")))
                .withPickupCode(1001)
                .withOrderStatus(OrderStatus.EM_PREPARACAO)
                .withPrice(new Money(new BigDecimal("26.00")))
                .withItems(List.of())
                .build();
        Order receivedOrder = Order.OrderBuilder.anOrder()
                .withId(new OrderId(UUID.fromString("d45b6c71-c7d7-4a04-bf92-190df9612ff3")))
                .withPickupCode(1001)
                .withOrderStatus(OrderStatus.RECEBIDO)
                .withPrice(new Money(new BigDecimal("26.00")))
                .withItems(List.of())
                .build();
        Order finishedOrder = Order.OrderBuilder.anOrder()
                .withId(new OrderId(UUID.fromString("d45b6c71-c7d7-4a04-bf92-190df9612ff4")))
                .withPickupCode(1001)
                .withOrderStatus(OrderStatus.FINALIZADO)
                .withPrice(new Money(new BigDecimal("26.00")))
                .withItems(List.of())
                .build();

        List<Order> mockOrders = Arrays.asList(
                readyOrder,
                preparingOrder,
                receivedOrder,
                finishedOrder

                );

        when(orderGateway.getAllExceptFinished()).thenReturn(mockOrders);

        // When
        getOrders.execute();

        // Then
        verify(orderGateway).getAllExceptFinished();
        verify(presenter).present(ordersResponseCaptor.capture());

        OrdersResponse response = ordersResponseCaptor.getValue();
        assertNotNull(response);
        assertEquals(4, response.getOrders().size());

    }

    @Test
    void execute_withNoOrders_shouldPresentEmptyResponse() {
        // Given
        when(orderGateway.getAllExceptFinished()).thenReturn(List.of());

        // When
        getOrders.execute();

        // Then
        verify(orderGateway).getAllExceptFinished();
        verify(presenter).present(ordersResponseCaptor.capture());

        OrdersResponse response = ordersResponseCaptor.getValue();
        assertNotNull(response);
        assertTrue(response.getOrders().isEmpty());
    }

}