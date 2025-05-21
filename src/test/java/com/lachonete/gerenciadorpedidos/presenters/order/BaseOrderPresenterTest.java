package com.lachonete.gerenciadorpedidos.presenters.order;

import com.lachonete.gerenciadorpedidos.entities.valueobject.ProductId;
import com.lachonete.gerenciadorpedidos.ports.presenters.order.OrderItemViewModel;
import com.lachonete.gerenciadorpedidos.ports.presenters.order.OrderViewModel;
import com.lachonete.gerenciadorpedidos.ports.usescases.order.get.OrderItemResponse;
import com.lachonete.gerenciadorpedidos.ports.usescases.order.get.OrderResponse;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class BaseOrderPresenterTest {

    @Test
    void mapToOrderItemViewModel_shouldMapAllFieldsCorrectly() {
        var productId = UUID.fromString("d3553fca-aaea-4574-8111-d8fa0c4ed4c7");

        // Given
        var itemId = 123L;
        OrderItemResponse response = OrderItemResponse.builder()
                .id(itemId)
                .quantity(2)
                .productId(productId)
                .subTotal(new BigDecimal("25.98"))
                .build();

        // When
        OrderItemViewModel viewModel = BaseOrderPresenter.mapToOrderItemViewModel(response);

        // Then
        assertNotNull(viewModel);
        assertEquals(itemId, viewModel.getId());
        assertEquals(2, viewModel.getQuantity());
        assertEquals(productId, viewModel.getProductId());
        assertEquals(new BigDecimal("25.98"), viewModel.getSubTotal());
    }

    @Test
    void mapToOrderItemViewModel_withNullInput_shouldThrowException() {
        assertThrows(NullPointerException.class, () -> BaseOrderPresenter.mapToOrderItemViewModel(null));
    }

    @Test
    void mapToOrderViewModel_withNullInput_shouldThrowException() {
        assertThrows(NullPointerException.class, () -> BaseOrderPresenter.mapToOrderViewModel(null));
    }


    @Test
    void mapToOrderItemViewModel_withNullFields_shouldHandleGracefully() {
        // Given
        OrderItemResponse response = OrderItemResponse.builder()
                .id(null)
                .productId(null)
                .subTotal(null)
                .build();

        // When
        OrderItemViewModel viewModel = BaseOrderPresenter.mapToOrderItemViewModel(response);

        // Then
        assertNotNull(viewModel);
        assertNull(viewModel.getId());
        assertNull(viewModel.getProductId());
        assertNull(viewModel.getSubTotal());
    }

}