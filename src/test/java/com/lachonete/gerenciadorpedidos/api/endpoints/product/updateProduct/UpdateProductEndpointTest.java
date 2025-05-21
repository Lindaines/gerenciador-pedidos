package com.lachonete.gerenciadorpedidos.api.endpoints.product.updateProduct;

import com.lachonete.gerenciadorpedidos.entities.valueobject.ProductCategory;
import com.lachonete.gerenciadorpedidos.ports.usescases.product.udpate.ProductRequest;
import com.lachonete.gerenciadorpedidos.ports.usescases.product.udpate.UpdateProductInputBoundary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdateProductEndpointTest {

    private UpdateProductInputBoundary updateProductUseCase;
    private UpdateProductEndpoint updateProductEndpoint;

    @BeforeEach
    void setUp() {
        // Initialize mock before each test
        updateProductUseCase = mock(UpdateProductInputBoundary.class);

        // Inject mock into the endpoint
        updateProductEndpoint = new UpdateProductEndpoint(updateProductUseCase);
    }

    @Test
    void execute_ShouldCallUseCaseAndReturnNoContentResponse() {
        // Given: A product ID and a valid update product request
        UUID productId = UUID.randomUUID();
        UpdateProductRequest request = UpdateProductRequest.builder()
                .name("Updated X-Burger")
                .description("Even more delicious burger")
                .price(new BigDecimal("29.99"))
                .category(ProductCategory.LANCHE)
                .images(List.of("http://example.com/updated-xb.jpg"))
                .build();

        // When: The execute method is called
        ResponseEntity responseEntity = updateProductEndpoint.execute(productId, request);

        // Then:
        // 1. The use case's execute method should be called exactly once
        verify(updateProductUseCase, times(1)).execute(any(ProductRequest.class));

        // 2. Capture the ProductRequest argument passed to the use case
        ArgumentCaptor<ProductRequest> requestCaptor = ArgumentCaptor.forClass(ProductRequest.class);
        verify(updateProductUseCase).execute(requestCaptor.capture());
        ProductRequest capturedRequest = requestCaptor.getValue();

        // 3. Verify the captured request's details
        assertNotNull(capturedRequest, "Captured ProductRequest should not be null.");
        assertEquals(productId, capturedRequest.getId(), "Product ID in request should match.");
        assertEquals(request.getName(), capturedRequest.getName(), "Name in request should match.");
        assertEquals(request.getDescription(), capturedRequest.getDescription(), "Description in request should match.");
        assertEquals(request.getPrice(), capturedRequest.getPrice(), "Price in request should match.");
        assertEquals(request.getCategory(), capturedRequest.getCategory(), "Category in request should match.");
        assertEquals(request.getImages(), capturedRequest.getImages(), "Images in request should match.");

        // 4. The response status should be 204 No Content
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode(), "Response status should be NO_CONTENT.");

        // 5. The response body should be null for a 204 No Content response
        assertNull(responseEntity.getBody(), "Response body should be null for a 204 No Content response.");
    }

    @Test
    void execute_ShouldHandleUseCaseThrowingException() {
        // Given: A product ID and a valid request
        UUID productId = UUID.randomUUID();
        UpdateProductRequest request = UpdateProductRequest.builder()
                .name("Failing Update")
                .price(new BigDecimal("1.00"))
                .category(ProductCategory.BEBIDA)
                .build();

        // And: The use case throws an exception (e.g., product not found, invalid data)
        doThrow(new RuntimeException("Business rule violation: product cannot be updated")).when(updateProductUseCase).execute(any(ProductRequest.class));

        // When/Then: Calling execute should rethrow the exception if not handled by endpoint
        assertThrows(RuntimeException.class, () -> updateProductEndpoint.execute(productId, request),
                "Should rethrow exception from use case if not handled by endpoint.");

        // Verify the use case was still called
        verify(updateProductUseCase, times(1)).execute(any(ProductRequest.class));
    }

    @Test
    void execute_ShouldHandleRequestWithNullDescriptionAndImages() {
        // Given: A product ID and a request with null description and images
        UUID productId = UUID.randomUUID();
        UpdateProductRequest request = UpdateProductRequest.builder()
                .name("Product with Nulls")
                .price(new BigDecimal("15.00"))
                .category(ProductCategory.ACOMPANHAMENTO)
                .description(null) // Explicitly null
                .images(null)     // Explicitly null
                .build();

        // When: The execute method is called
        ResponseEntity responseEntity = updateProductEndpoint.execute(productId, request);

        // Then:
        verify(updateProductUseCase, times(1)).execute(any(ProductRequest.class));
        ArgumentCaptor<ProductRequest> requestCaptor = ArgumentCaptor.forClass(ProductRequest.class);
        verify(updateProductUseCase).execute(requestCaptor.capture());
        ProductRequest capturedRequest = requestCaptor.getValue();

        assertEquals(productId, capturedRequest.getId());
        assertEquals(request.getName(), capturedRequest.getName());
        assertNull(capturedRequest.getDescription(), "Description in captured request should be null.");
        assertNull(capturedRequest.getImages(), "Images in captured request should be null.");

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }

    @Test
    void execute_ShouldHandleNullProductId() {
        // Given: A null product ID and a valid request
        UUID productId = null;
        UpdateProductRequest request = UpdateProductRequest.builder()
                .name("Null ID Product")
                .price(new BigDecimal("5.00"))
                .category(ProductCategory.BEBIDA)
                .build();

        // When: The execute method is called with a null productId
        updateProductEndpoint.execute(productId, request);

        // Then:
        // Verify the use case was called with a ProductRequest containing a null ID
        ArgumentCaptor<ProductRequest> requestCaptor = ArgumentCaptor.forClass(ProductRequest.class);
        verify(updateProductUseCase, times(1)).execute(requestCaptor.capture());
        ProductRequest capturedRequest = requestCaptor.getValue();

        assertNotNull(capturedRequest, "Captured ProductRequest should not be null.");
        assertNull(capturedRequest.getId(), "Product ID in captured request should be null.");
        assertEquals(request.getName(), capturedRequest.getName(), "Name should still be mapped.");
        // It's up to the use case to handle the null ID (e.g., throw IllegalArgumentException or similar)
        // This test only verifies that the endpoint correctly passes the null ID to the use case.
    }
}