package com.lachonete.gerenciadorpedidos.api.endpoints.order.update;

import com.lachonete.gerenciadorpedidos.entities.valueobject.OrderStatus;
import com.lachonete.gerenciadorpedidos.entities.valueobject.ProductCategory;
import com.lachonete.gerenciadorpedidos.ports.usescases.order.update.UpdateOrderInputBoundary;
import com.lachonete.gerenciadorpedidos.ports.usescases.order.update.UpdateOrderRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdateOrderStatusEndpointTest {

    private UpdateOrderInputBoundary updateOrderUseCase;
    private UpdateOrderStatusEndpoint updateOrderStatusEndpoint;

    @BeforeEach
    void setUp() {
        // Initialize mock before each test
        updateOrderUseCase = mock(UpdateOrderInputBoundary.class);

        // Inject mock into the endpoint
        updateOrderStatusEndpoint = new UpdateOrderStatusEndpoint(updateOrderUseCase);
    }

    @Test
    void execute_ShouldCallUseCaseAndReturnOkResponse() {
        // Given: An order ID and a valid status update request
        UUID orderId = UUID.randomUUID();
        var newStatus = OrderStatus.EM_PREPARACAO;
        UpdateOrderStatusRequest request = UpdateOrderStatusRequest.builder()
                .orderStatus(newStatus)
                .build();

        // When: The execute method is called
        ResponseEntity responseEntity = updateOrderStatusEndpoint.execute(orderId, request);

        // Then:
        // 1. The use case's execute method should be called exactly once
        verify(updateOrderUseCase, times(1)).execute(any(UpdateOrderRequest.class));

        // 2. Capture the UpdateOrderRequest argument passed to the use case
        ArgumentCaptor<UpdateOrderRequest> requestCaptor = ArgumentCaptor.forClass(UpdateOrderRequest.class);
        verify(updateOrderUseCase).execute(requestCaptor.capture());
        UpdateOrderRequest capturedRequest = requestCaptor.getValue();

        // 3. Verify the captured request's ID and status
        assertNotNull(capturedRequest, "Captured UpdateOrderRequest should not be null.");
        assertEquals(orderId, capturedRequest.getId(), "Order ID in request should match.");
        assertEquals(newStatus, capturedRequest.getStatus(), "Status in request should match.");

        // 4. The response status should be 200 OK
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode(), "Response status should be OK.");

        // 5. The response body should be null (since it's .build())
        assertNull(responseEntity.getBody(), "Response body should be null for an OK build.");
    }

    @Test
    void execute_ShouldHandleUseCaseThrowingException() {
        // Given: An order ID and a valid status update request
        UUID orderId = UUID.randomUUID();
        var newStatus = OrderStatus.FINALIZADO;
        UpdateOrderStatusRequest request = UpdateOrderStatusRequest.builder()
                .orderStatus(newStatus)
                .build();

        // And: The use case throws an exception (e.g., order not found, invalid state transition)
        doThrow(new RuntimeException("Order not found or invalid status transition")).when(updateOrderUseCase).execute(any(UpdateOrderRequest.class));

        // When/Then: Calling execute should throw the exception if not handled by endpoint
        assertThrows(RuntimeException.class, () -> updateOrderStatusEndpoint.execute(orderId, request),
                "Should rethrow exception from use case if not handled by endpoint.");

        // Verify the use case was still called
        verify(updateOrderUseCase, times(1)).execute(any(UpdateOrderRequest.class));
    }


}