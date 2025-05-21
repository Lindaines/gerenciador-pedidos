package com.lachonete.gerenciadorpedidos.api.endpoints.customer.get; // Package for GetCustomersEndpoint

import com.lachonete.gerenciadorpedidos.entities.valueobject.ProductCategory;
import com.lachonete.gerenciadorpedidos.ports.presenters.product.ProductViewModel;
import com.lachonete.gerenciadorpedidos.ports.presenters.product.ProductsOutputBoundary;
import com.lachonete.gerenciadorpedidos.ports.presenters.product.ProductsViewModel;
import com.lachonete.gerenciadorpedidos.ports.usescases.product.get.GetProductsInputBoundary; // This is the use case actually used
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetCustomersEndpointTest { // Renamed from GetProductsEndpointTest to match the class name

    private GetProductsInputBoundary getProductsUseCase; // Matches the dependency name
    private ProductsOutputBoundary productsPresenter;     // Matches the dependency name
    private GetCustomersEndpoint getCustomersEndpoint;    // The class under test

    @BeforeEach
    void setUp() {
        // Initialize mocks before each test
        getProductsUseCase = mock(GetProductsInputBoundary.class);
        productsPresenter = mock(ProductsOutputBoundary.class);

        // Inject mocks into the endpoint
        getCustomersEndpoint = new GetCustomersEndpoint(getProductsUseCase, productsPresenter);
    }

    @Test
    void execute_ShouldCallUseCaseAndReturnOkResponseWithProducts() {
        // Given: A list of expected ProductViewModel
        ProductViewModel product1 = ProductViewModel.builder()
                .id(UUID.randomUUID().toString())
                .name("Burguer")
                .description("Tasty burger")
                .price(new BigDecimal("15.99"))
                .category(ProductCategory.LANCHE)
                .build();

        ProductViewModel product2 = ProductViewModel.builder()
                .id(UUID.randomUUID().toString())
                .name("Soda")
                .description("Refreshing drink")
                .price(new BigDecimal("5.00"))
                .category(ProductCategory.BEBIDA)
                .build();

        // And: The presenter is configured to return a ProductsViewModel with these products
        ProductsViewModel expectedProductsViewModel = ProductsViewModel.builder()
                .getProductsViewModel(product1) // Use the singular builder method
                .getProductsViewModel(product2) // Use the singular builder method
                .build();

        when(productsPresenter.getViewModel()).thenReturn(expectedProductsViewModel);

        // When: The execute method is called
        ResponseEntity responseEntity = getCustomersEndpoint.execute();

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
        assertEquals(expectedProductsViewModel.getProducts().get(0).getName(), actualProductsViewModel.getProducts().get(0).getName(), "Product 1 name should match.");
        assertEquals(expectedProductsViewModel.getProducts().get(1).getName(), actualProductsViewModel.getProducts().get(1).getName(), "Product 2 name should match.");
        // Add more detailed assertions if needed, comparing all fields of individual ProductViewModels
        assertEquals(expectedProductsViewModel.getProducts().get(0).getId(), actualProductsViewModel.getProducts().get(0).getId());
        assertEquals(expectedProductsViewModel.getProducts().get(0).getPrice(), actualProductsViewModel.getProducts().get(0).getPrice());
        assertEquals(expectedProductsViewModel.getProducts().get(0).getCategory(), actualProductsViewModel.getProducts().get(0).getCategory());
    }

    @Test
    void execute_ShouldHandleNoProductsFound() {
        // Given: The presenter is configured to return an empty ProductsViewModel
        ProductsViewModel emptyProductsViewModel = ProductsViewModel.builder().build(); // An empty list
        when(productsPresenter.getViewModel()).thenReturn(emptyProductsViewModel);

        // When: The execute method is called
        ResponseEntity responseEntity = getCustomersEndpoint.execute();

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
        ResponseEntity responseEntity = getCustomersEndpoint.execute();

        // Then:
        // 1. The use case should still be called
        verify(getProductsUseCase, times(1)).execute();

        // 2. The presenter's getViewModel should be called
        verify(productsPresenter, times(1)).getViewModel();

        // 3. The response status should be 200 OK (as per endpoint logic)
        // Note: Returning null for a list of resources usually implies an empty list or 404, not a null body.
        // This test reflects the current endpoint's direct return of presenter.getViewModel().
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode(), "Response status should be OK.");

        // 4. The response body should be null
        assertNull(responseEntity.getBody(), "Response body should be null if ViewModel is null.");
    }

    @Test
    void execute_ShouldHandleUseCaseThrowingException() {
        // Given: The use case throws an exception
        doThrow(new RuntimeException("Internal server error during product retrieval")).when(getProductsUseCase).execute();

        // When/Then: Calling execute should throw the exception if not handled by endpoint
        assertThrows(RuntimeException.class, () -> getCustomersEndpoint.execute(),
                "Should rethrow exception from use case if not handled by endpoint.");

        // Verify the use case was called
        verify(getProductsUseCase, times(1)).execute();
        // Verify presenter was NOT called if use case failed before reaching it
        verify(productsPresenter, never()).getViewModel();
    }
}