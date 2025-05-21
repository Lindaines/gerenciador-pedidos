package com.lachonete.gerenciadorpedidos.presenters.order;

import com.lachonete.gerenciadorpedidos.ports.presenters.order.OrderCreatedViewModel;
import com.lachonete.gerenciadorpedidos.ports.usescases.order.add.CheckoutOrderResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class OrderCreatedPresenterTest {

    private OrderCreatedPresenter presenter;

    @BeforeEach
    void setUp() {
        presenter = new OrderCreatedPresenter();
    }

    @Test
    void present_shouldCreateViewModelWithCorrectValues() {
        // Given
        UUID orderId = UUID.randomUUID();
        Integer pickupCode = 123;
        String paymentId = "PAY789";
        CheckoutOrderResponse response = new CheckoutOrderResponse(orderId, pickupCode, paymentId);

        // When
        presenter.present(response);
        OrderCreatedViewModel viewModel = presenter.getViewModel();

        // Then
        assertNotNull(viewModel);
        assertEquals(orderId.toString(), viewModel.getId());
        assertEquals(pickupCode, viewModel.getPickupCode());
        assertEquals(paymentId, viewModel.getPaymentId());
    }

    @Test
    void getViewModel_beforePresentation_shouldReturnNull() {
        assertNull(presenter.getViewModel());
    }

    @Test
    void present_withNullResponse_shouldThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> presenter.present(null));
    }

//    @Test
//    void present_withNullFields_shouldHandleGracefully() {
//        // Given
//        CheckoutOrderResponse response = new CheckoutOrderResponse(null, null, null);
//
//        // When
//        presenter.present(response);
//        OrderCreatedViewModel viewModel = presenter.getViewModel();
//
//        // Then
//        assertNotNull(viewModel);
//        assertNull(viewModel.getId());
//        assertNull(viewModel.getPickupCode());
//        assertNull(viewModel.getPaymentId());
//    }

    @Test
    void present_shouldUpdateViewModelOnSubsequentCalls() {
        // Given
        UUID firstOrderId = UUID.randomUUID();
        CheckoutOrderResponse firstResponse = new CheckoutOrderResponse(firstOrderId, 1, "PAY1");

        UUID secondOrderId = UUID.randomUUID();
        CheckoutOrderResponse secondResponse = new CheckoutOrderResponse(secondOrderId, 1, "PAY2");

        // When
        presenter.present(firstResponse);
        OrderCreatedViewModel firstViewModel = presenter.getViewModel();

        presenter.present(secondResponse);
        OrderCreatedViewModel secondViewModel = presenter.getViewModel();

        // Then
        assertEquals(firstOrderId.toString(), firstViewModel.getId());
        assertEquals(secondOrderId.toString(), secondViewModel.getId());
        assertNotEquals(firstViewModel.getId(), secondViewModel.getId());
    }

    @Test
    void present_withEmptyPickupCode_shouldPreserveEmptyValue() {
        // Given
        CheckoutOrderResponse response = new CheckoutOrderResponse(UUID.randomUUID(), null, "PAY123");

        // When
        presenter.present(response);
        OrderCreatedViewModel viewModel = presenter.getViewModel();

        // Then
        assertNull(viewModel.getPickupCode());
    }

}
