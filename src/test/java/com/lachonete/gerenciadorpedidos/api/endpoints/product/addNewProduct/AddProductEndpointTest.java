package com.lachonete.gerenciadorpedidos.api.endpoints.product.addNewProduct;

import com.lachonete.gerenciadorpedidos.entities.valueobject.ProductCategory;
import com.lachonete.gerenciadorpedidos.ports.presenters.product.ProductCreatedOutputBoundary;
import com.lachonete.gerenciadorpedidos.ports.presenters.product.ProductCreatedViewModel;
import com.lachonete.gerenciadorpedidos.ports.usescases.product.add.AddProductInputBoundary;
import com.lachonete.gerenciadorpedidos.ports.usescases.product.add.AddProductRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AddProductEndpointTest {

    private AddProductInputBoundary addProductUseCase;
    private ProductCreatedOutputBoundary productCreatedPresenter;
    private AddProductEndpoint addProductEndpoint;

    @BeforeEach
    void setUp() {
        // Initialize mocks before each test
        addProductUseCase = mock(AddProductInputBoundary.class);
        productCreatedPresenter = mock(ProductCreatedOutputBoundary.class);

        // Inject mocks into the endpoint
        addProductEndpoint = new AddProductEndpoint(addProductUseCase, productCreatedPresenter);

        // Mock HttpServletRequest for URI.create with MessageFormat
        HttpServletRequest mockRequest = mock(HttpServletRequest.class);
        when(mockRequest.getRequestURL()).thenReturn(new StringBuffer("http://localhost:8080/api/v1/products"));
        when(mockRequest.getRequestURI()).thenReturn("/api/v1/products");
        when(mockRequest.getContextPath()).thenReturn("");
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(mockRequest));
    }


    @Test
    void execute_ShouldHandleUseCaseThrowingException() {
        // Given: A valid NewProductRequest
        NewProductRequest request = NewProductRequest.builder()
                .name("Invalid Product")
                .price(new BigDecimal("10.00"))
                .category(ProductCategory.BEBIDA)
                .build();

        // And: The use case throws an exception (e.g., product name already exists, invalid data)
        doThrow(new RuntimeException("Business rule violation: product name already exists")).when(addProductUseCase).execute(any(AddProductRequest.class));

        // When/Then: Calling execute should throw the exception if not handled by endpoint
        assertThrows(RuntimeException.class, () -> addProductEndpoint.execute(request),
                "Should rethrow exception from use case if not handled by endpoint.");

        // Verify the use case was still called
        verify(addProductUseCase, times(1)).execute(any(AddProductRequest.class));
        // Verify presenter was NOT called if use case failed before reaching it
        verify(productCreatedPresenter, never()).getViewModel();
    }

    @Test
    void execute_ShouldHandleRequestWithNullDescriptionAndImages() {
        // Given: A NewProductRequest with name, price, category but null description and images
        NewProductRequest request = NewProductRequest.builder()
                .name("Simple Burger")
                .price(new BigDecimal("18.50"))
                .category(ProductCategory.LANCHE)
                .description(null) // Explicitly null
                .images(null)     // Explicitly null
                .build();

        // And: The presenter returns a ProductCreatedViewModel
        UUID createdProductId = UUID.randomUUID();
        ProductCreatedViewModel createdViewModel = ProductCreatedViewModel.builder()
                .id(createdProductId.toString())
                .build();
        when(productCreatedPresenter.getViewModel()).thenReturn(createdViewModel);

        // When: The execute method is called
        ResponseEntity responseEntity = addProductEndpoint.execute(request);

        // Then:
        verify(addProductUseCase, times(1)).execute(any(AddProductRequest.class));
        ArgumentCaptor<AddProductRequest> requestCaptor = ArgumentCaptor.forClass(AddProductRequest.class);
        verify(addProductUseCase).execute(requestCaptor.capture());
        AddProductRequest capturedRequest = requestCaptor.getValue();

        assertNull(capturedRequest.getDescription(), "Description in captured request should be null.");
        assertNull(capturedRequest.getImages(), "Images in captured request should be null.");

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        ProductCreatedViewModel actualViewModel = (ProductCreatedViewModel) responseEntity.getBody();
    }
}