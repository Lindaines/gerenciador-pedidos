package com.lachonete.gerenciadorpedidos.api.endpoints.product.getProducts;

import com.lachonete.gerenciadorpedidos.entities.valueobject.ProductCategory;
import com.lachonete.gerenciadorpedidos.ports.presenters.product.ProductOutputBoundary;
import com.lachonete.gerenciadorpedidos.ports.presenters.product.ProductViewModel;
import com.lachonete.gerenciadorpedidos.ports.usescases.product.get.GetProductByIdRequest;
import com.lachonete.gerenciadorpedidos.ports.usescases.product.get.GetProductInputBoundary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetProductByIdEndpointTest {

    private GetProductInputBoundary getProductUseCase;
    private ProductOutputBoundary productPresenter;
    private GetProductByIdEndpoint getProductByIdEndpoint;

    @BeforeEach
    void setUp() {
        // Initialize mocks before each test
        getProductUseCase = mock(GetProductInputBoundary.class);
        productPresenter = mock(ProductOutputBoundary.class);

        // Inject mocks into the endpoint
        getProductByIdEndpoint = new GetProductByIdEndpoint(getProductUseCase, productPresenter);
    }

    @Test
    void execute_ShouldCallUseCaseAndReturnOkResponseWithProduct() {
        // Given: A product ID
        UUID productId = UUID.randomUUID();

        // And: The presenter is configured to return a ProductViewModel
        ProductViewModel expectedProduct = ProductViewModel.builder()
                .id(productId.toString())
                .name("Coca-Cola Zero")
                .description("Refrigerante sabor cola sem açúcar")
                .price(new BigDecimal("7.00"))
                .category(ProductCategory.BEBIDA)
                .images(List.of("http://example.com/coke-zero.jpg"))
                .build();

        when(productPresenter.getViewModel()).thenReturn(expectedProduct);

        // When: The execute method is called
        ResponseEntity responseEntity = getProductByIdEndpoint.execute(productId);

        // Then:
        // 1. The use case's execute method should be called exactly once
        verify(getProductUseCase, times(1)).execute(any(GetProductByIdRequest.class));

        // 2. Capture the GetProductByIdRequest argument passed to the use case
        ArgumentCaptor<GetProductByIdRequest> requestCaptor = ArgumentCaptor.forClass(GetProductByIdRequest.class);
        verify(getProductUseCase).execute(requestCaptor.capture());
        GetProductByIdRequest capturedRequest = requestCaptor.getValue();

        // 3. Verify the captured request's ID
        assertNotNull(capturedRequest, "Captured GetProductByIdRequest should not be null.");
        assertEquals(productId, capturedRequest.getId(), "Product ID in request should match.");

        // 4. The presenter's getViewModel method should be called
        verify(productPresenter, times(1)).getViewModel();

        // 5. The response status should be 200 OK
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode(), "Response status should be OK.");

        // 6. The response body should be the ProductViewModel from the presenter
        assertNotNull(responseEntity.getBody(), "Response body should not be null.");
        assertTrue(responseEntity.getBody() instanceof ProductViewModel, "Response body should be a ProductViewModel.");
        ProductViewModel actualProduct = (ProductViewModel) responseEntity.getBody();

        assertEquals(expectedProduct.getId(), actualProduct.getId(), "Product ID should match.");
        assertEquals(expectedProduct.getName(), actualProduct.getName(), "Product Name should match.");
        assertEquals(expectedProduct.getPrice(), actualProduct.getPrice(), "Product Price should match.");
        // Add more detailed assertions for other fields if necessary
    }

    @Test
    void execute_ShouldHandleProductNotFound() {
        // Given: A product ID for a non-existent product
        UUID nonExistentProductId = UUID.randomUUID();

        // And: The presenter is configured to return null (simulating product not found by use case)
        when(productPresenter.getViewModel()).thenReturn(null);

        // When: The execute method is called
        ResponseEntity responseEntity = getProductByIdEndpoint.execute(nonExistentProductId);

        // Then:
        // 1. The use case should still be called
        verify(getProductUseCase, times(1)).execute(any(GetProductByIdRequest.class));

        // 2. The presenter's getViewModel should be called
        verify(productPresenter, times(1)).getViewModel();

        // 3. The response status should be 200 OK
        // (Note: This depends on presenter's behavior. If presenter returns null,
        //  the endpoint returns 200 OK with null body. A real-world scenario might
        //  prefer 404 Not Found, which would require the presenter or an exception
        //  handler to communicate 'not found' status to the endpoint or its caller.)
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode(), "Response status should be OK.");

        // 4. The response body should be null
        assertNull(responseEntity.getBody(), "Response body should be null if ViewModel is null (product not found).");
    }


    @Test
    void execute_ShouldHandleUseCaseThrowingException() {
        // Given: A product ID, and the use case throws an exception
        UUID productId = UUID.randomUUID();
        doThrow(new RuntimeException("Database error during product retrieval")).when(getProductUseCase).execute(any(GetProductByIdRequest.class));

        // When/Then: Calling execute should rethrow the exception if not handled by endpoint
        assertThrows(RuntimeException.class, () -> getProductByIdEndpoint.execute(productId),
                "Should rethrow exception from use case if not handled by endpoint.");

        // Verify the use case was still called
        verify(getProductUseCase, times(1)).execute(any(GetProductByIdRequest.class));
        // Verify presenter was NOT called if use case failed before reaching it
        verify(productPresenter, never()).getViewModel();
    }

    @Test
    void execute_ShouldHandleNullProductId() {
        // Given: A null product ID
        UUID productId = null;

        // When: The execute method is called with a null productId
        getProductByIdEndpoint.execute(productId); // No exception expected from the endpoint method itself

        // Then:
        // Verify the use case was called with a GetProductByIdRequest containing a null ID
        ArgumentCaptor<GetProductByIdRequest> requestCaptor = ArgumentCaptor.forClass(GetProductByIdRequest.class);
        verify(getProductUseCase, times(1)).execute(requestCaptor.capture());
        GetProductByIdRequest capturedRequest = requestCaptor.getValue();

        assertNotNull(capturedRequest, "Captured GetProductByIdRequest should not be null.");
        assertNull(capturedRequest.getId(), "Product ID in captured request should be null.");

        // If the presenter is mocked to return a valid view model, we test that path
        ProductViewModel expectedProduct = ProductViewModel.builder()
                .id(null) // ID could be null in view model if creation failed or was empty
                .name("Default Product")
                .price(BigDecimal.ONE)
                .category(ProductCategory.LANCHE)
                .build();
        when(productPresenter.getViewModel()).thenReturn(expectedProduct);

        ResponseEntity responseEntity = getProductByIdEndpoint.execute(productId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedProduct, responseEntity.getBody());
    }
}