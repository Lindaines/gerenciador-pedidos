package com.lachonete.gerenciadorpedidos.usecases.order.update;

import com.lachonete.gerenciadorpedidos.entities.Order;
import com.lachonete.gerenciadorpedidos.entities.valueobject.Money;
import com.lachonete.gerenciadorpedidos.entities.valueobject.OrderId;
import com.lachonete.gerenciadorpedidos.entities.valueobject.OrderStatus;
import com.lachonete.gerenciadorpedidos.ports.database.OrderGateway;
import com.lachonete.gerenciadorpedidos.ports.usescases.order.update.UpdateOrderRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateOrderTest {

    @Mock
    private OrderGateway orderGateway;

    private UpdateOrder updateOrder;

    @BeforeEach
    void setUp() {
        updateOrder = new UpdateOrder(orderGateway);
    }

    @Test
    void execute_shouldUpdateOrderStatusSuccessfully() {
        // Given
        UUID orderId = UUID.randomUUID();
        UpdateOrderRequest request = new UpdateOrderRequest(orderId, OrderStatus.PRONTO);

        Order mockOrder = Order.OrderBuilder.anOrder()
                .withId(new OrderId(UUID.randomUUID()))
                .withPickupCode(1001)
                .withOrderStatus(OrderStatus.EM_PREPARACAO)
                .withPrice(new Money(new BigDecimal("26.00")))
                .withItems(List.of())
                .build();

        when(orderGateway.getById(orderId)).thenReturn(mockOrder);
        doNothing().when(orderGateway).updateStatus(orderId, OrderStatus.PRONTO);

        // When
        updateOrder.execute(request);

        // Then
        verify(orderGateway).getById(orderId);
        verify(orderGateway).updateStatus(orderId, OrderStatus.PRONTO);
    }

    @Test
    void execute_withNonExistentOrder_shouldThrowOrderNotFoundException() {
        // Given
        UUID nonExistentOrderId = UUID.randomUUID();
        UpdateOrderRequest request = new UpdateOrderRequest(nonExistentOrderId, OrderStatus.PRONTO);

        when(orderGateway.getById(nonExistentOrderId)).thenReturn(null);

        // When & Then
        assertThrows(OrderGateway.OrderNotFoundException.class, () -> {
            updateOrder.execute(request);
        });

        verify(orderGateway).getById(nonExistentOrderId);
        verify(orderGateway, never()).updateStatus(any(), any());
    }


    @Test
    void validateOrder_withNonExistentOrder_shouldThrowOrderNotFoundException() {
        // Given
        UUID nonExistentOrderId = UUID.randomUUID();
        when(orderGateway.getById(nonExistentOrderId)).thenReturn(null);

        // When & Then
        assertThrows(OrderGateway.OrderNotFoundException.class, () -> {
            updateOrder.validateOrder(nonExistentOrderId);
        });
    }



    @Test
    void execute_withNullRequest_shouldThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> updateOrder.execute(null));
    }


}