package com.lachonete.gerenciadorpedidos.api.endpoints.order;

import com.lachonete.gerenciadorpedidos.ports.presenters.order.OrderCreatedOutputBoundary;
import com.lachonete.gerenciadorpedidos.ports.presenters.order.OrderCreatedViewModel;
import com.lachonete.gerenciadorpedidos.ports.usescases.order.add.CheckoutOrderInputBoundary;
import com.lachonete.gerenciadorpedidos.ports.usescases.order.add.CheckoutOrderRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.net.URI;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CheckoutOrderEndpointTest {

    private CheckoutOrderInputBoundary checkoutOrderUseCase;
    private OrderCreatedOutputBoundary orderCreatedPresenter;
    private CheckoutOrderEndpoint checkoutOrderEndpoint;

    // Define the default S3 bucket prefix as it would be in a unit test environment for OrderCreatedViewModel
    private final String DEFAULT_S3_BUCKET_PREFIX = "https://payments-lanchonete.s3.amazonaws.com/";

    @BeforeEach
    void setUp() {
        // Initialize mocks before each test
        checkoutOrderUseCase = mock(CheckoutOrderInputBoundary.class);
        orderCreatedPresenter = mock(OrderCreatedOutputBoundary.class);

        // Inject mocks into the endpoint
        checkoutOrderEndpoint = new CheckoutOrderEndpoint(checkoutOrderUseCase, orderCreatedPresenter);

        // Mock HttpServletRequest for ServletUriComponentsBuilder.fromCurrentRequest()
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getRequestURL()).thenReturn(new StringBuffer("http://localhost:8080/api/v1/orders"));
        when(mockRequest.getRequestURI()).thenReturn("/api/v1/orders");
        when(mockRequest.getContextPath()).thenReturn("");
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(mockRequest));
    }


    @Test
    void execute_ShouldHandleEmptyOrderItems() {
        // Given: A NewOrderRequest with an empty list of items
        NewOrderRequest request = NewOrderRequest.builder()
                .items(Collections.emptyList())
                .build();

        // And: The presenter returns a ViewModel (e.g., for an order with 0 total)
        UUID orderId = UUID.randomUUID();
        String paymentId = "empty-order-payment-" + UUID.randomUUID().toString();
        Integer pickupCode = 54321;
        OrderCreatedViewModel viewModel = new OrderCreatedViewModel(orderId.toString(), pickupCode, paymentId);
        when(orderCreatedPresenter.getViewModel()).thenReturn(viewModel);

        // When: The execute method is called
        ResponseEntity responseEntity = checkoutOrderEndpoint.execute(request);

        // Then:
        verify(checkoutOrderUseCase, times(1)).execute(any(CheckoutOrderRequest.class));
        ArgumentCaptor<CheckoutOrderRequest> requestCaptor = ArgumentCaptor.forClass(CheckoutOrderRequest.class);
        verify(checkoutOrderUseCase).execute(requestCaptor.capture());
        CheckoutOrderRequest capturedRequest = requestCaptor.getValue();

        assertNotNull(capturedRequest.getItems(), "Captured CheckoutOrderRequest items should not be null.");
        assertTrue(capturedRequest.getItems().isEmpty(), "Captured CheckoutOrderRequest items should be empty.");

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode(), "Response status should be CREATED.");
        assertNotNull(responseEntity.getBody(), "Response body should not be null.");
        assertTrue(responseEntity.getBody() instanceof OrderCreatedViewModel, "Response body should be an OrderCreatedViewModel.");
        OrderCreatedViewModel actualViewModel = (OrderCreatedViewModel) responseEntity.getBody();
        assertEquals(viewModel.getId(), actualViewModel.getId());
        assertEquals(viewModel.getPickupCode(), actualViewModel.getPickupCode());
        assertEquals(viewModel.getPaymentId(), actualViewModel.getPaymentId());

        URI expectedLocation = URI.create(MessageFormat.format("/api/v1/orders/{0}", orderId.toString()));
        assertEquals(expectedLocation, responseEntity.getHeaders().getLocation(), "Location header should be correct.");
    }


    @Test
    void execute_ShouldHandleUseCaseFailureGracefully() {
        // Given: A valid NewOrderRequest
        OrderItemRequest newOrderItem = OrderItemRequest.builder()
                .productId(UUID.randomUUID())
                .quantity(1)
                .price(new BigDecimal("10.00"))
                .subtotal(new BigDecimal("10.00"))
                .build();
        NewOrderRequest request = NewOrderRequest.builder()
                .items(List.of(newOrderItem))
                .build();

        // And: The use case throws an exception (e.g., business validation failed)
        doThrow(new RuntimeException("Business rule violation: invalid product")).when(checkoutOrderUseCase).execute(any(CheckoutOrderRequest.class));

        // When/Then: Calling execute should throw an exception if not handled by endpoint
        assertThrows(RuntimeException.class, () -> checkoutOrderEndpoint.execute(request),
                "Should rethrow exception from use case if not handled by endpoint.");

        // Verify the use case was still called
        verify(checkoutOrderUseCase, times(1)).execute(any(CheckoutOrderRequest.class));
        // Verify presenter was NOT called if use case failed before reaching it
        verify(orderCreatedPresenter, never()).getViewModel();
    }

}