package com.lachonete.gerenciadorpedidos.presenters.order;

import com.lachonete.gerenciadorpedidos.entities.valueobject.OrderStatus;
import com.lachonete.gerenciadorpedidos.ports.presenters.order.OrdersViewModel;
import com.lachonete.gerenciadorpedidos.ports.usescases.order.get.OrderItemResponse;
import com.lachonete.gerenciadorpedidos.ports.usescases.order.get.OrderResponse;
import com.lachonete.gerenciadorpedidos.ports.usescases.order.get.OrdersResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class OrdersPresenterTest {

    private OrdersPresenter presenter;

    @BeforeEach
    void setUp() {
        presenter = new OrdersPresenter();
    }

    @Test
    void present_shouldMapMultipleOrdersToViewModel() {
        // Given
        UUID orderId1 = UUID.randomUUID();
        UUID orderId2 = UUID.randomUUID();

        OrderItemResponse item = OrderItemResponse.builder()
                .id(1L)
                .productId(UUID.randomUUID())
                .quantity(2)
                .subTotal(new BigDecimal("19.98"))
                .build();

        OrderResponse order1 = OrderResponse.builder()
                .id(orderId1)
                .price(new BigDecimal("19.98"))
                .pickUpCode(101)
                .orderStatus(OrderStatus.EM_PREPARACAO)
                .items(List.of(item))
                .build();

        OrderResponse order2 = OrderResponse.builder()
                .id(orderId2)
                .price(new BigDecimal("29.99"))
                .pickUpCode(102)
                .orderStatus(OrderStatus.PRONTO)
                .items(List.of())
                .build();

        OrdersResponse response = OrdersResponse.builder()
                .orders(List.of(order1, order2))
                .build();

        // When
        presenter.present(response);
        OrdersViewModel viewModel = presenter.getViewModel();

        // Then
        assertNotNull(viewModel);
        assertEquals(2, viewModel.getOrders().size());

        // Verify first order
        assertEquals(orderId1, viewModel.getOrders().get(0).getId());
        assertEquals(101, viewModel.getOrders().get(0).getPickUpCode());
        assertEquals(OrderStatus.EM_PREPARACAO, viewModel.getOrders().get(0).getOrderStatus());
        assertEquals(1, viewModel.getOrders().get(0).getItems().size());

        // Verify second order
        assertEquals(orderId2, viewModel.getOrders().get(1).getId());
        assertEquals(102, viewModel.getOrders().get(1).getPickUpCode());
        assertEquals(OrderStatus.PRONTO, viewModel.getOrders().get(1).getOrderStatus());
        assertTrue(viewModel.getOrders().get(1).getItems().isEmpty());
    }

    @Test
    void present_withEmptyOrdersList_shouldCreateEmptyViewModel() {
        // Given
        OrdersResponse response = OrdersResponse.builder()
                .orders(List.of())
                .build();

        // When
        presenter.present(response);
        OrdersViewModel viewModel = presenter.getViewModel();

        // Then
        assertNotNull(viewModel);
        assertTrue(viewModel.getOrders().isEmpty());
    }

    @Test
    void getViewModel_beforePresentation_shouldReturnNull() {
        assertNull(presenter.getViewModel());
    }

    @Test
    void present_withNullResponse_shouldThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> presenter.present(null));
    }

    @Test
    void present_withOrderContainingMultipleItems_shouldMapAllItems() {
        // Given
        OrderItemResponse item1 = OrderItemResponse.builder()
                .id(1L)
                .productId(UUID.randomUUID())
                .quantity(1)
                .subTotal(new BigDecimal("10.00"))
                .build();

        OrderItemResponse item2 = OrderItemResponse.builder()
                .id(2L)
                .productId(UUID.randomUUID())
                .quantity(2)
                .subTotal(new BigDecimal("20.00"))
                .build();

        OrderResponse order = OrderResponse.builder()
                .id(UUID.randomUUID())
                .price(new BigDecimal("30.00"))
                .items(List.of(item1, item2))
                .build();

        OrdersResponse response = OrdersResponse.builder()
                .orders(List.of(order))
                .build();

        // When
        presenter.present(response);
        OrdersViewModel viewModel = presenter.getViewModel();

        // Then
        assertEquals(1, viewModel.getOrders().size());
        assertEquals(2, viewModel.getOrders().get(0).getItems().size());
        assertEquals(1L, viewModel.getOrders().get(0).getItems().get(0).getId());
        assertEquals(2L, viewModel.getOrders().get(0).getItems().get(1).getId());
    }

}