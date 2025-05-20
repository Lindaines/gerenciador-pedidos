package com.lachonete.gerenciadorpedidos.usecases.product.get;

import com.lachonete.gerenciadorpedidos.entities.Product;
import com.lachonete.gerenciadorpedidos.entities.valueobject.*;
import com.lachonete.gerenciadorpedidos.ports.database.ProductGateway;
import com.lachonete.gerenciadorpedidos.ports.presenters.product.ProductOutputBoundary;
import com.lachonete.gerenciadorpedidos.ports.usescases.product.get.GetProductByIdRequest;
import com.lachonete.gerenciadorpedidos.ports.usescases.product.get.ProductResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetProductByIdTest {

    @Mock
    private ProductOutputBoundary presenter;

    @Mock
    private ProductGateway productGateway;

    @Captor
    private ArgumentCaptor<ProductResponse> responseCaptor;

    private GetProductById getProductById;

    @BeforeEach
    void setUp() {
        getProductById = new GetProductById(presenter, productGateway);
    }

    @Test
    void execute_shouldGetProductAndPresentResponse() {
        // Given
        UUID productId = UUID.randomUUID();
        GetProductByIdRequest request = new GetProductByIdRequest(productId);

        Product mockProduct = Product.ProductBuilder.aProduct()
                .withId(new ProductId(productId))
                .withName("Cheeseburger")
                .withDescription("Delicious burger")
                .withPrice(new Money(new BigDecimal("12.99")))
                .withCategory(ProductCategory.LANCHE)
                .withImages(List.of(new Image("burger.jpg")))
                .build();

        when(productGateway.getById(productId)).thenReturn(mockProduct);

        // When
        getProductById.execute(request);

        // Then
        verify(productGateway).getById(productId);
        verify(presenter).present(responseCaptor.capture());

        ProductResponse response = responseCaptor.getValue();
        assertNotNull(response);
        assertEquals(productId, response.getId());
        assertEquals("Cheeseburger", response.getName());
        assertEquals("Delicious burger", response.getDescription());
        assertEquals(new BigDecimal("12.99"), response.getPrice());
        assertEquals(ProductCategory.LANCHE, response.getCategory());
        assertEquals(1, response.getImages().size());
    }

    @Test
    void execute_withNonExistentProduct_shouldThrowProductNotFoundException() {
        // Given
        UUID nonExistentId = UUID.randomUUID();
        GetProductByIdRequest request = new GetProductByIdRequest(nonExistentId);

        when(productGateway.getById(nonExistentId)).thenReturn(null);

        // When & Then
        assertThrows(ProductGateway.ProductNotFoundException.class, () -> {
            getProductById.execute(request);
        });

        verify(productGateway).getById(nonExistentId);
        verify(presenter, never()).present(any());
    }

    @Test
    void execute_withNullRequest_shouldThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> getProductById.execute(null));
    }


    @Test
    void execute_shouldPresentCorrectProductResponse() {
        // Given
        UUID productId = UUID.randomUUID();
        GetProductByIdRequest request = new GetProductByIdRequest(productId);

        Product mockProduct = Product.ProductBuilder.aProduct()
                .withId(new ProductId(productId))
                .withName("Test Burger")
                .withDescription("Test Description")
                .withPrice(new Money(new BigDecimal("9.99")))
                .withCategory(ProductCategory.LANCHE)
                .withImages(List.of(new Image("test.jpg")))
                .build();

        when(productGateway.getById(productId)).thenReturn(mockProduct);

        // When
        getProductById.execute(request);

        // Then
        verify(presenter).present(responseCaptor.capture());
        ProductResponse response = responseCaptor.getValue();

        assertEquals(mockProduct.getId().getValue(), response.getId());
        assertEquals(mockProduct.getName(), response.getName());
        assertEquals(mockProduct.getDescription(), response.getDescription());
        assertEquals(mockProduct.getPrice().getAmount(), response.getPrice());
        assertEquals(mockProduct.getCategory(), response.getCategory());
        assertEquals(1, response.getImages().size());
    }
}