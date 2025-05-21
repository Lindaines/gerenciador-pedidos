package com.lachonete.gerenciadorpedidos.api.endpoints.product.getProducts;

import com.lachonete.gerenciadorpedidos.entities.valueobject.ProductCategory;
import com.lachonete.gerenciadorpedidos.ports.presenters.product.ProductViewModel;
import com.lachonete.gerenciadorpedidos.ports.presenters.product.ProductsOutputBoundary;
import com.lachonete.gerenciadorpedidos.ports.presenters.product.ProductsViewModel;
import com.lachonete.gerenciadorpedidos.ports.usescases.product.get.GetProductsInputBoundary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetProductsEndpointTest {

    private GetProductsInputBoundary getProductsUseCase;
    private ProductsOutputBoundary productsPresenter;
    private GetProductsEndpoint getProductsEndpoint;

    @BeforeEach
    void setUp() {
        // Initialize mocks before each test
        getProductsUseCase = mock(GetProductsInputBoundary.class);
        productsPresenter = mock(ProductsOutputBoundary.class);

        // Inject mocks into the endpoint
        getProductsEndpoint = new GetProductsEndpoint(getProductsUseCase, productsPresenter);
    }

    @Test
    void execute_ShouldCallUseCaseAndReturnOkResponseWithProducts() {
        // Given: A list of expected ProductViewModel
        ProductViewModel product1 = ProductViewModel.builder()
                .id(UUID.randomUUID().toString())
                .name("X-Burger")
                .price(new BigDecimal("20.00"))
                .category(ProductCategory.LANCHE)
                .build();

        ProductViewModel product2 = ProductViewModel.builder()
                .id(UUID.randomUUID().toString())
                .name("Batata Frita")
                .price(new BigDecimal("12.50"))
                .category(ProductCategory.ACOMPANHAMENTO)
                .build();

        // And: The presenter is configured to return a ProductsViewModel with these products
        ProductsViewModel expectedProductsViewModel = ProductsViewModel.builder()
                .getProductsViewModel(product1) // Corrected builder method
                .getProductsViewModel(product2) // Corrected builder method
                .build();

        when(productsPresenter.getViewModel()).thenReturn(expectedProductsViewModel);

        // When: The execute method is called
        ResponseEntity responseEntity = getProductsEndpoint.execute();

        // Then:
        // 1. The use case's execute method should be called exactly once
        verify(getProductsUseCase, times(1)).execute();

        // 2. The presenter's getViewModel method should be called
        verify(productsPresenter, times(1)).getViewModel();

        // 3. The response status should be 200 OK
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode(), "Response status should be OK.");

        // 4. The response body should be the ProductsViewModel from the presenter
        assertNotNull(responseEntity.getBody(), "Response body should not be null.");
        assertTrue(responseEntity.getBody() instanceof ProductsViewModel, "Response body should be an instance of ProductsViewModel.");
        ProductsViewModel actualProductsViewModel = (ProductsViewModel) responseEntity.getBody();

        assertEquals(expectedProductsViewModel.getProducts().size(), actualProductsViewModel.getProducts().size(), "Number of products should match.");
        assertEquals(expectedProductsViewModel.getProducts().get(0).getId(), actualProductsViewModel.getProducts().get(0).getId(), "Product 1 ID should match.");
        assertEquals(expectedProductsViewModel.getProducts().get(1).getId(), actualProductsViewModel.getProducts().get(1).getId(), "Product 2 ID should match.");
        // Add more detailed assertions if needed, comparing all fields of individual ProductViewModels
        assertEquals(expectedProductsViewModel.getProducts().get(0).getName(), actualProductsViewModel.getProducts().get(0).getName());
        assertEquals(expectedProductsViewModel.getProducts().get(0).getPrice(), actualProductsViewModel.getProducts().get(0).getPrice());
    }

    @Test
    void execute_ShouldHandleNoProductsFound() {
        // Given: The presenter is configured to return an empty ProductsViewModel
        ProductsViewModel emptyProductsViewModel = ProductsViewModel.builder().build(); // An empty list
        when(productsPresenter.getViewModel()).thenReturn(emptyProductsViewModel);

        // When: The execute method is called
        ResponseEntity responseEntity = getProductsEndpoint.execute();

        // Then:
        // 1. The use case should still be called
        verify(getProductsUseCase, times(1)).execute();

        // 2. The presenter's getViewModel should be called
        verify(productsPresenter, times(1)).getViewModel();

        // 3. The response status should be 200 OK
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode(), "Response status should be OK.");

        // 4. The response body should be an empty ProductsViewModel
        assertNotNull(responseEntity.getBody(), "Response body should not be null.");
        assertTrue(responseEntity.getBody() instanceof ProductsViewModel, "Response body should be an instance of ProductsViewModel.");
        ProductsViewModel actualProductsViewModel = (ProductsViewModel) responseEntity.getBody();
        assertTrue(actualProductsViewModel.getProducts().isEmpty(), "The products list in the view model should be empty.");
    }

    @Test
    void execute_ShouldHandlePresenterReturningNullViewModel() {
        // Given: The presenter is configured to return null for getViewModel()
        when(productsPresenter.getViewModel()).thenReturn(null);

        // When: The execute method is called
        ResponseEntity responseEntity = getProductsEndpoint.execute();

        // Then:
        // 1. The use case should still be called
        verify(getProductsUseCase, times(1)).execute();

        // 2. The presenter's getViewModel should be called
        verify(productsPresenter, times(1)).getViewModel();

        // 3. The response status should be 200 OK (as per endpoint logic)
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode(), "Response status should be OK.");

        // 4. The response body should be null
        assertNull(responseEntity.getBody(), "Response body should be null if ViewModel is null.");
    }

    @Test
    void execute_ShouldHandleUseCaseThrowingException() {
        // Given: The use case throws an exception
        doThrow(new RuntimeException("Database error during product retrieval")).when(getProductsUseCase).execute();

        // When/Then: Calling execute should throw the exception if not handled by endpoint
        assertThrows(RuntimeException.class, () -> getProductsEndpoint.execute(),
                "Should rethrow exception from use case if not handled by endpoint.");

        // Verify the use case was called
        verify(getProductsUseCase, times(1)).execute();
        // Verify presenter was NOT called if use case failed before reaching it
        verify(productsPresenter, never()).getViewModel();
    }
}