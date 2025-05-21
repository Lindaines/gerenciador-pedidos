package com.lachonete.gerenciadorpedidos.api.endpoints.order;

import com.lachonete.gerenciadorpedidos.entities.valueobject.OrderStatus;
import com.lachonete.gerenciadorpedidos.ports.presenters.order.OrderViewModel;
import com.lachonete.gerenciadorpedidos.ports.presenters.order.OrdersOutputBoundary;
import com.lachonete.gerenciadorpedidos.ports.presenters.order.OrdersViewModel;
import com.lachonete.gerenciadorpedidos.ports.usescases.order.get.GetOrdersInputBoundary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetOrdersEndpointTest {

    private GetOrdersInputBoundary getOrdersUseCase;
    private OrdersOutputBoundary ordersPresenter;
    private GetOrdersEndpoint getOrdersEndpoint;

    @BeforeEach
    void setUp() {
        // Initialize mocks before each test
        getOrdersUseCase = mock(GetOrdersInputBoundary.class);
        ordersPresenter = mock(OrdersOutputBoundary.class);

        // Inject mocks into the endpoint
        getOrdersEndpoint = new GetOrdersEndpoint(getOrdersUseCase, ordersPresenter);
    }

    @Test
    void execute_ShouldCallUseCaseAndReturnOkResponseWithOrders() {
        // Given: A list of expected OrderViewModel
        OrderViewModel order1 = OrderViewModel.builder()
                .id(UUID.randomUUID())
                .orderStatus(OrderStatus.CRIADO)
                .price(new BigDecimal("50.00"))
                .build();

        OrderViewModel order2 = OrderViewModel.builder()
                .id(UUID.randomUUID())
                .orderStatus(OrderStatus.CRIADO)
                .price(new BigDecimal("75.50"))
                .build();

        // And: The presenter is configured to return a OrdersViewModel with these orders
        OrdersViewModel expectedOrdersViewModel = OrdersViewModel.builder()
                .getOrdersViewModel(order1) // Use the singular builder method
                .getOrdersViewModel(order2) // Use the singular builder method
                .build();

        when(ordersPresenter.getViewModel()).thenReturn(expectedOrdersViewModel);

        // When: The execute method is called
        ResponseEntity responseEntity = getOrdersEndpoint.execute();

        // Then:
        // 1. The use case's execute method should be called exactly once
        verify(getOrdersUseCase, times(1)).execute();

        // 2. The presenter's getViewModel method should be called
        verify(ordersPresenter, times(1)).getViewModel();

        // 3. The response status should be 200 OK
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode(), "Response status should be OK.");

        // 4. The response body should be the OrdersViewModel from the presenter
        assertNotNull(responseEntity.getBody(), "Response body should not be null.");
        assertTrue(responseEntity.getBody() instanceof OrdersViewModel, "Response body should be an instance of OrdersViewModel.");
        OrdersViewModel actualOrdersViewModel = (OrdersViewModel) responseEntity.getBody();

        assertEquals(expectedOrdersViewModel.getOrders().size(), actualOrdersViewModel.getOrders().size(), "Number of orders should match.");
        assertEquals(expectedOrdersViewModel.getOrders().get(0).getId(), actualOrdersViewModel.getOrders().get(0).getId(), "Order 1 ID should match.");
        assertEquals(expectedOrdersViewModel.getOrders().get(1).getId(), actualOrdersViewModel.getOrders().get(1).getId(), "Order 2 ID should match.");
        // Add more detailed assertions if needed, comparing all fields of individual OrderViewModels
        assertEquals(expectedOrdersViewModel.getOrders().get(0).getOrderStatus(), actualOrdersViewModel.getOrders().get(0).getOrderStatus());
        assertEquals(expectedOrdersViewModel.getOrders().get(0).getOrderStatus(), actualOrdersViewModel.getOrders().get(0).getOrderStatus());
    }

    @Test
    void execute_ShouldHandleNoOrdersFound() {
        // Given: The presenter is configured to return an empty OrdersViewModel
        OrdersViewModel emptyOrdersViewModel = OrdersViewModel.builder().build(); // An empty list
        when(ordersPresenter.getViewModel()).thenReturn(emptyOrdersViewModel);

        // When: The execute method is called
        ResponseEntity responseEntity = getOrdersEndpoint.execute();

        // Then:
        // 1. The use case should still be called
        verify(getOrdersUseCase, times(1)).execute();

        // 2. The presenter's getViewModel should be called
        verify(ordersPresenter, times(1)).getViewModel();

        // 3. The response status should be 200 OK
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode(), "Response status should be OK.");

        // 4. The response body should be an empty OrdersViewModel
        assertNotNull(responseEntity.getBody(), "Response body should not be null.");
        assertTrue(responseEntity.getBody() instanceof OrdersViewModel, "Response body should be an instance of OrdersViewModel.");
        OrdersViewModel actualOrdersViewModel = (OrdersViewModel) responseEntity.getBody();
        assertTrue(actualOrdersViewModel.getOrders().isEmpty(), "The orders list in the view model should be empty.");
    }

    @Test
    void execute_ShouldHandlePresenterReturningNullViewModel() {
        // Given: The presenter is configured to return null for getViewModel()
        when(ordersPresenter.getViewModel()).thenReturn(null);

        // When: The execute method is called
        ResponseEntity responseEntity = getOrdersEndpoint.execute();

        // Then:
        // 1. The use case should still be called
        verify(getOrdersUseCase, times(1)).execute();

        // 2. The presenter's getViewModel should be called
        verify(ordersPresenter, times(1)).getViewModel();

        // 3. The response status should be 200 OK (as per endpoint logic)
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode(), "Response status should be OK.");

        // 4. The response body should be null
        assertNull(responseEntity.getBody(), "Response body should be null if ViewModel is null.");
    }

    @Test
    void execute_ShouldHandleUseCaseThrowingException() {
        // Given: The use case throws an exception
        doThrow(new RuntimeException("Database error during order retrieval")).when(getOrdersUseCase).execute();

        // When/Then: Calling execute should throw the exception if not handled by endpoint
        assertThrows(RuntimeException.class, () -> getOrdersEndpoint.execute(),
                "Should rethrow exception from use case if not handled by endpoint.");

        // Verify the use case was called
        verify(getOrdersUseCase, times(1)).execute();
        // Verify presenter was NOT called if use case failed before reaching it
        verify(ordersPresenter, never()).getViewModel();
    }
}