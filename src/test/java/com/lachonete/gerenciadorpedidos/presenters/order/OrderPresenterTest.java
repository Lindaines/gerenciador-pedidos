package com.lachonete.gerenciadorpedidos.presenters.order;

import com.lachonete.gerenciadorpedidos.entities.valueobject.OrderStatus;
import com.lachonete.gerenciadorpedidos.ports.presenters.order.OrderViewModel;
import com.lachonete.gerenciadorpedidos.ports.usescases.order.get.OrderItemResponse;
import com.lachonete.gerenciadorpedidos.ports.usescases.order.get.OrderResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class OrderPresenterTest {

    private OrderPresenter presenter;

    @BeforeEach
    void setUp() {
        presenter = new OrderPresenter();
    }

    @Test
    void present_shouldMapCompleteOrderToViewModel() {
        // Given
        var orderId = UUID.randomUUID();
        OrderItemResponse item = OrderItemResponse.builder()
                .id(1L)
                .productId(UUID.randomUUID())
                .quantity(2)
                .subTotal(new BigDecimal("19.98"))
                .build();

        OrderResponse response = OrderResponse.builder()
                .id(orderId)
                .price(new BigDecimal("19.98"))
                .pickUpCode(1)
                .orderStatus(OrderStatus.EM_PREPARACAO)
                .items(List.of(item))
                .build();

        // When
        presenter.present(response);
        OrderViewModel viewModel = presenter.getViewModel();

        // Then
        assertNotNull(viewModel);
        assertEquals(orderId, viewModel.getId());
        assertEquals(new BigDecimal("19.98"), viewModel.getPrice());
        assertEquals(1, viewModel.getPickUpCode());
        assertEquals(OrderStatus.EM_PREPARACAO, viewModel.getOrderStatus());
        assertEquals(1, viewModel.getItems().size());
        assertEquals(1L, viewModel.getItems().get(0).getId());
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
    void present_withEmptyOrder_shouldHandleCorrectly() {
        // Given
        OrderResponse response = OrderResponse.builder()
                .id(UUID.randomUUID())
                .price(BigDecimal.ZERO)
                .items(List.of())
                .build();

        // When
        presenter.present(response);
        OrderViewModel viewModel = presenter.getViewModel();

        // Then
        assertNotNull(viewModel);
        assertTrue(viewModel.getItems().isEmpty());
    }

    @Test
    void present_withMultipleItems_shouldMapAllItems() {
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

        OrderResponse response = OrderResponse.builder()
                .id(UUID.randomUUID())
                .price(new BigDecimal("30.00"))
                .items(List.of(item1, item2))
                .build();

        // When
        presenter.present(response);
        OrderViewModel viewModel = presenter.getViewModel();

        // Then
        assertEquals(2, viewModel.getItems().size());
        assertEquals(1L, viewModel.getItems().get(0).getId());
        assertEquals(2L, viewModel.getItems().get(1).getId());
    }

}