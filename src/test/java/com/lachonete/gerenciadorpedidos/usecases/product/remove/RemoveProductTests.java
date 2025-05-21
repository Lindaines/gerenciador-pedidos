package com.lachonete.gerenciadorpedidos.usecases.product.remove;

import com.lachonete.gerenciadorpedidos.ports.database.ProductGateway;
import com.lachonete.gerenciadorpedidos.ports.usescases.product.remove.RemoveProductRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RemoveProductTest {

    @Mock
    private ProductGateway productGateway;

    private RemoveProduct removeProduct;

    @BeforeEach
    void setUp() {
        removeProduct = new RemoveProduct(productGateway);
    }

    @Test
    void execute_shouldDeleteProductWithValidId() {
        // Given
        UUID productId = UUID.randomUUID();
        RemoveProductRequest request = new RemoveProductRequest(productId);

        doNothing().when(productGateway).delete(productId);

        // When
        removeProduct.execute(request);

        // Then
        verify(productGateway).delete(productId);
        verifyNoMoreInteractions(productGateway);
    }

    @Test
    void execute_shouldCallGatewayWithCorrectId() {
        // Given
        UUID expectedId = UUID.randomUUID();
        RemoveProductRequest request = new RemoveProductRequest(expectedId);

        // When
        removeProduct.execute(request);

        // Then
        verify(productGateway).delete(expectedId);
    }

    @Test
    void execute_withNullRequest_shouldThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> removeProduct.execute(null));
    }

    @Test
    void execute_shouldOnlyCallDeleteOnce() {
        // Given
        UUID productId = UUID.randomUUID();
        RemoveProductRequest request = new RemoveProductRequest(productId);

        // When
        removeProduct.execute(request);

        // Then
        verify(productGateway, times(1)).delete(productId);
    }

    @Test
    void execute_shouldNotInteractWithGatewayAfterDelete() {
        // Given
        UUID productId = UUID.randomUUID();
        RemoveProductRequest request = new RemoveProductRequest(productId);

        // When
        removeProduct.execute(request);

        // Then
        verify(productGateway).delete(productId);
        verifyNoMoreInteractions(productGateway);
    }

    @Test
    void execute_withMultipleCalls_shouldCallGatewayEachTime() {
        // Given
        UUID productId1 = UUID.randomUUID();
        UUID productId2 = UUID.randomUUID();
        RemoveProductRequest request1 = new RemoveProductRequest(productId1);
        RemoveProductRequest request2 = new RemoveProductRequest(productId2);

        // When
        removeProduct.execute(request1);
        removeProduct.execute(request2);

        // Then
        verify(productGateway).delete(productId1);
        verify(productGateway).delete(productId2);
        verify(productGateway, times(2)).delete(any());
    }
}