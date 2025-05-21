package com.lachonete.gerenciadorpedidos.api.endpoints.product.deleteProducts;

import com.lachonete.gerenciadorpedidos.ports.usescases.product.remove.RemoveProductInputBoundary;
import com.lachonete.gerenciadorpedidos.ports.usescases.product.remove.RemoveProductRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeleteProductByIdEndpointTest {

    private RemoveProductInputBoundary removeProductUseCase;
    private DeleteProductByIdEndpoint deleteProductByIdEndpoint;

    @BeforeEach
    void setUp() {
        // Initialize mock before each test
        removeProductUseCase = mock(RemoveProductInputBoundary.class);

        // Inject mock into the endpoint
        deleteProductByIdEndpoint = new DeleteProductByIdEndpoint(removeProductUseCase);
    }

    @Test
    void execute_ShouldCallUseCaseAndReturnNoContentResponse() {
        // Given: A product ID to be deleted
        UUID productId = UUID.randomUUID();

        // When: The execute method is called
        ResponseEntity responseEntity = deleteProductByIdEndpoint.execute(productId);

        // Then:
        // 1. The use case's execute method should be called exactly once
        verify(removeProductUseCase, times(1)).execute(any(RemoveProductRequest.class));

        // 2. Capture the RemoveProductRequest argument passed to the use case
        ArgumentCaptor<RemoveProductRequest> requestCaptor = ArgumentCaptor.forClass(RemoveProductRequest.class);
        verify(removeProductUseCase).execute(requestCaptor.capture());
        RemoveProductRequest capturedRequest = requestCaptor.getValue();

        // 3. Verify the captured request's ID
        assertNotNull(capturedRequest, "Captured RemoveProductRequest should not be null.");
        assertEquals(productId, capturedRequest.getId(), "Product ID in request should match.");

        // 4. The response status should be 204 No Content
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode(), "Response status should be NO_CONTENT.");

        // 5. The response body should be null for a 204 No Content response
        assertNull(responseEntity.getBody(), "Response body should be null for a 204 No Content response.");
    }

    @Test
    void execute_ShouldHandleUseCaseThrowingException() {
        // Given: A product ID, and the use case throws an exception
        UUID productId = UUID.randomUUID();
        doThrow(new RuntimeException("Product not found or cannot be deleted")).when(removeProductUseCase).execute(any(RemoveProductRequest.class));

        // When/Then: Calling execute should rethrow the exception if not handled by endpoint
        assertThrows(RuntimeException.class, () -> deleteProductByIdEndpoint.execute(productId),
                "Should rethrow exception from use case if not handled by endpoint.");

        // Verify the use case was still called
        verify(removeProductUseCase, times(1)).execute(any(RemoveProductRequest.class));
    }

    @Test
    void execute_ShouldHandleNullProductId() {
        // Given: A null product ID
        UUID productId = null;

        // When: The execute method is called with a null productId
        deleteProductByIdEndpoint.execute(productId); // No exception expected from the endpoint method itself

        // Then:
        // Verify the use case was called with a RemoveProductRequest containing a null ID
        ArgumentCaptor<RemoveProductRequest> requestCaptor = ArgumentCaptor.forClass(RemoveProductRequest.class);
        verify(removeProductUseCase, times(1)).execute(requestCaptor.capture());
        RemoveProductRequest capturedRequest = requestCaptor.getValue();

        assertNotNull(capturedRequest, "Captured RemoveProductRequest should not be null.");
        assertNull(capturedRequest.getId(), "Product ID in captured request should be null.");

        // It's up to the use case to handle the null ID (e.g., throw IllegalArgumentException or similar)
        // This test only verifies that the endpoint correctly passes the null ID to the use case.
    }
}