package com.lachonete.gerenciadorpedidos.usecases.product.update;

import com.lachonete.gerenciadorpedidos.entities.Product;
import com.lachonete.gerenciadorpedidos.entities.valueobject.*;
import com.lachonete.gerenciadorpedidos.ports.database.ProductGateway;
import com.lachonete.gerenciadorpedidos.ports.usescases.product.udpate.ProductRequest;
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
class UpdateProductTest {

    @Mock
    private ProductGateway productGateway;

    @Captor
    private ArgumentCaptor<Product> productCaptor;

    private UpdateProduct updateProduct;

    @BeforeEach
    void setUp() {
        updateProduct = new UpdateProduct(productGateway);
    }

    @Test
    void execute_shouldUpdateProductSuccessfully() {
        // Given
        UUID productId = UUID.randomUUID();
        ProductRequest request = new ProductRequest(
                productId,
                "Updated Burger",
                "Updated description",
                new BigDecimal("15.99"),
                ProductCategory.LANCHE,
                List.of("new-image.jpg")
        );

        doNothing().when(productGateway).update(any(Product.class));

        // When
        updateProduct.execute(request);

        // Then
        verify(productGateway).update(productCaptor.capture());
        Product updatedProduct = productCaptor.getValue();

        assertEquals(productId, updatedProduct.getId().getValue());
        assertEquals("Updated Burger", updatedProduct.getName());
        assertEquals("Updated description", updatedProduct.getDescription());
        assertEquals(new BigDecimal("15.99"), updatedProduct.getPrice().getAmount());
        assertEquals(ProductCategory.LANCHE, updatedProduct.getCategory());
        assertEquals(1, updatedProduct.getImages().size());
        assertEquals("new-image.jpg", updatedProduct.getImages().get(0).urlPath);
    }

    @Test
    void execute_shouldMapRequestToProductCorrectly() {
        // Given
        ProductRequest request = new ProductRequest(
                UUID.randomUUID(),
                "Pizza",
                "Delicious pizza",
                new BigDecimal("25.99"),
                ProductCategory.LANCHE,
                List.of("pizza1.jpg", "pizza2.jpg")
        );

        // When
        updateProduct.execute(request);

        // Then
        verify(productGateway).update(productCaptor.capture());
        Product updatedProduct = productCaptor.getValue();

        assertEquals(request.getId(), updatedProduct.getId().getValue());
        assertEquals(request.getName(), updatedProduct.getName());
        assertEquals(request.getDescription(), updatedProduct.getDescription());
        assertEquals(request.getPrice(), updatedProduct.getPrice().getAmount());
        assertEquals(request.getCategory(), updatedProduct.getCategory());
        assertEquals(2, updatedProduct.getImages().size());
        assertEquals("pizza1.jpg", updatedProduct.getImages().get(0).urlPath);
    }

    @Test
    void execute_withEmptyImages_shouldUpdateProduct() {
        // Given
        ProductRequest request = new ProductRequest(
                UUID.randomUUID(),
                "Salad",
                "Healthy salad",
                new BigDecimal("10.99"),
                ProductCategory.ACOMPANHAMENTO,
                List.of()
        );

        // When
        updateProduct.execute(request);

        // Then
        verify(productGateway).update(productCaptor.capture());
        Product updatedProduct = productCaptor.getValue();

        assertTrue(updatedProduct.getImages().isEmpty());
    }

    @Test
    void execute_withNullRequest_shouldThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> updateProduct.execute(null));
    }

    @Test
    void execute_shouldCallGatewayExactlyOnce() {
        // Given
        ProductRequest request = createValidRequest();

        // When
        updateProduct.execute(request);

        // Then
        verify(productGateway, times(1)).update(any(Product.class));
    }

    @Test
    void execute_whenGatewayThrowsException_shouldPropagateIt() {
        // Given
        ProductRequest request = createValidRequest();
        doThrow(new RuntimeException("Database error")).when(productGateway).update(any(Product.class));

        // When & Then
        assertThrows(RuntimeException.class, () -> updateProduct.execute(request));
    }

    private ProductRequest createValidRequest() {
        return new ProductRequest(
                UUID.randomUUID(),
                "Valid Product",
                "Valid Description",
                new BigDecimal("9.99"),
                ProductCategory.LANCHE,
                List.of("valid.jpg")
        );
    }
}