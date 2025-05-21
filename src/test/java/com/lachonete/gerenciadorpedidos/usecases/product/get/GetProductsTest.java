package com.lachonete.gerenciadorpedidos.usecases.product.get;

import com.lachonete.gerenciadorpedidos.entities.Product;
import com.lachonete.gerenciadorpedidos.entities.valueobject.*;
import com.lachonete.gerenciadorpedidos.ports.database.ProductGateway;
import com.lachonete.gerenciadorpedidos.ports.presenters.product.ProductsOutputBoundary;
import com.lachonete.gerenciadorpedidos.ports.usescases.product.get.ProductsResponse;
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
class GetProductsTest {

    @Mock
    private ProductsOutputBoundary presenter;

    @Mock
    private ProductGateway productGateway;

    @Captor
    private ArgumentCaptor<ProductsResponse> responseCaptor;

    private GetProducts getProducts;

    @BeforeEach
    void setUp() {
        getProducts = new GetProducts(presenter, productGateway);
    }

    @Test
    void execute_shouldGetAllProductsAndPresentResponse() {
        // Given
        UUID productId1 = UUID.randomUUID();
        UUID productId2 = UUID.randomUUID();

        Product product1 = createTestProduct(
                productId1,
                "Cheeseburger",
                "Delicious burger",
                new BigDecimal("12.99"),
                ProductCategory.LANCHE,
                List.of(new Image("burger.jpg"))
        );

        Product product2 = createTestProduct(
                productId2,
                "Fries",
                "Crispy fries",
                new BigDecimal("5.99"),
                ProductCategory.ACOMPANHAMENTO,
                List.of(new Image("fries.jpg"))
        );

        when(productGateway.getAll()).thenReturn(List.of(product1, product2));

        // When
        getProducts.execute();

        // Then
        verify(productGateway).getAll();
        verify(presenter).present(responseCaptor.capture());

        ProductsResponse response = responseCaptor.getValue();
        assertNotNull(response);
        assertEquals(2, response.getProducts().size());

        // Verify first product
        assertEquals(productId1, response.getProducts().get(0).getId());
        assertEquals("Cheeseburger", response.getProducts().get(0).getName());
        assertEquals(ProductCategory.LANCHE, response.getProducts().get(0).getCategory());

        // Verify second product
        assertEquals(productId2, response.getProducts().get(1).getId());
        assertEquals("Fries", response.getProducts().get(1).getName());
        assertEquals(ProductCategory.ACOMPANHAMENTO, response.getProducts().get(1).getCategory());
    }

    @Test
    void execute_withEmptyProductList_shouldPresentEmptyResponse() {
        // Given
        when(productGateway.getAll()).thenReturn(List.of());

        // When
        getProducts.execute();

        // Then
        verify(presenter).present(responseCaptor.capture());
        ProductsResponse response = responseCaptor.getValue();
        assertNotNull(response);
        assertTrue(response.getProducts().isEmpty());
    }

    @Test
    void execute_shouldConvertAllProductsToResponses() {
        // Given
        Product product = createTestProduct(
                UUID.randomUUID(),
                "Soda",
                "Cold drink",
                new BigDecimal("4.99"),
                ProductCategory.BEBIDA,
                List.of(new Image("soda.jpg"))
        );

        when(productGateway.getAll()).thenReturn(List.of(product));

        // When
        getProducts.execute();

        // Then
        verify(presenter).present(responseCaptor.capture());
        ProductsResponse response = responseCaptor.getValue();

        assertEquals(1, response.getProducts().size());
        assertEquals(product.getName(), response.getProducts().get(0).getName());
        assertEquals(product.getDescription(), response.getProducts().get(0).getDescription());
        assertEquals(product.getPrice().getAmount(), response.getProducts().get(0).getPrice());
        assertEquals(product.getCategory(), response.getProducts().get(0).getCategory());
        assertEquals(1, response.getProducts().get(0).getImages().size());
    }

    @Test
    void execute_shouldMaintainProductOrderFromGateway() {
        // Given
        Product product1 = createTestProduct(UUID.randomUUID(), "First", "Desc", BigDecimal.ONE, ProductCategory.LANCHE, List.of());
        Product product2 = createTestProduct(UUID.randomUUID(), "Second", "Desc", BigDecimal.ONE, ProductCategory.LANCHE, List.of());

        when(productGateway.getAll()).thenReturn(List.of(product1, product2));

        // When
        getProducts.execute();

        // Then
        verify(presenter).present(responseCaptor.capture());
        ProductsResponse response = responseCaptor.getValue();

        assertEquals("First", response.getProducts().get(0).getName());
        assertEquals("Second", response.getProducts().get(1).getName());
    }

    @Test
    void execute_shouldHandleProductsWithEmptyImages() {
        // Given
        Product product = createTestProduct(
                UUID.randomUUID(),
                "Water",
                "Mineral water",
                new BigDecimal("2.99"),
                ProductCategory.BEBIDA,
                List.of()
        );

        when(productGateway.getAll()).thenReturn(List.of(product));

        // When
        getProducts.execute();

        // Then
        verify(presenter).present(responseCaptor.capture());
        ProductsResponse response = responseCaptor.getValue();

        assertTrue(response.getProducts().get(0).getImages().isEmpty());
    }

    private Product createTestProduct(UUID id, String name, String description,
                                      BigDecimal price, ProductCategory category,
                                      List<Image> images) {
        return Product.ProductBuilder.aProduct()
                .withId(new ProductId(id))
                .withName(name)
                .withDescription(description)
                .withPrice(new Money(price))
                .withCategory(category)
                .withImages(images)
                .build();
    }
}