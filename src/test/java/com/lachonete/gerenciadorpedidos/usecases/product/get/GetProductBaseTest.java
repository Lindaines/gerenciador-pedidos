package com.lachonete.gerenciadorpedidos.usecases.product.get;

import com.lachonete.gerenciadorpedidos.entities.Product;
import com.lachonete.gerenciadorpedidos.entities.valueobject.Image;
import com.lachonete.gerenciadorpedidos.entities.valueobject.Money;
import com.lachonete.gerenciadorpedidos.entities.valueobject.ProductCategory;
import com.lachonete.gerenciadorpedidos.entities.valueobject.ProductId;
import com.lachonete.gerenciadorpedidos.ports.usescases.product.get.ProductResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class GetProductBaseTest {

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

    @Test
    @DisplayName("Should convert complete product to response correctly")
    void makeProductResponse_shouldConvertProductToResponseCorrectly() {
        // Given
        UUID productId = UUID.randomUUID();
        var image = new Image("http://s3object.com/burger.jpg");
        Product product = createTestProduct(
                productId,
                "Cheeseburger",
                "Delicious cheeseburger with cheddar",
                new BigDecimal("12.99"),
                ProductCategory.LANCHE,
                List.of(image)
        );

        // When
        ProductResponse response = GetProductBase.makeProductResponse(product);

        // Then
        assertNotNull(response);
        assertEquals(productId, response.getId());
        assertEquals("Cheeseburger", response.getName());
        assertEquals("Delicious cheeseburger with cheddar", response.getDescription());
        assertEquals(new BigDecimal("12.99"), response.getPrice());
        assertEquals(ProductCategory.LANCHE, response.getCategory());
        assertEquals(1, response.getImages().size());
        assertEquals("http://s3object.com/burger.jpg", response.getImages().get(0));
    }

    @Test
    @DisplayName("Should handle empty images list")
    void makeProductResponse_withEmptyImages_shouldReturnEmptyImageList() {
        // Given
        Product product = createTestProduct(
                UUID.randomUUID(),
                "Veggie Burger",
                "Plant-based burger",
                new BigDecimal("10.99"),
                ProductCategory.LANCHE,
                List.of()
        );

        // When
        ProductResponse response = GetProductBase.makeProductResponse(product);

        // Then
        assertNotNull(response);
        assertTrue(response.getImages().isEmpty());
    }

    @Test
    @DisplayName("Should throw NPE when product is null")
    void makeProductResponse_withNullProduct_shouldThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> GetProductBase.makeProductResponse(null));
    }

    @Test
    @DisplayName("Should handle zero price correctly")
    void makeProductResponse_withZeroPrice_shouldReturnZero() {
        // Given
        Product product = createTestProduct(
                UUID.randomUUID(),
                "Free Sample",
                "Trial product",
                BigDecimal.ZERO,
                ProductCategory.LANCHE,
                List.of()
        );

        // When
        ProductResponse response = GetProductBase.makeProductResponse(product);

        // Then
        assertNotNull(response);
        assertEquals(BigDecimal.ZERO, response.getPrice());
    }

    @Test
    @DisplayName("Should handle multiple images correctly")
    void makeProductResponse_withMultipleImages_shouldReturnAllImages() {
        // Given
        Product product = createTestProduct(
                UUID.randomUUID(),
                "Combo Meal",
                "Burger with fries and drink",
                new BigDecimal("25.99"),
                ProductCategory.LANCHE,
                List.of(
                        new Image("http://s3object.com/combo1.jpg"),
                        new Image("http://s3object.com/combo2.jpg")
                )
        );

        // When
        ProductResponse response = GetProductBase.makeProductResponse(product);

        // Then
        assertEquals(2, response.getImages().size());
        assertTrue(response.getImages().contains("http://s3object.com/combo1.jpg"));
        assertTrue(response.getImages().contains("http://s3object.com/combo2.jpg"));
    }

    @ParameterizedTest
    @EnumSource(ProductCategory.class)
    @DisplayName("Should handle all product categories correctly")
    void makeProductResponse_shouldHandleAllCategories(ProductCategory category) {
        // Given
        Product product = createTestProduct(
                UUID.randomUUID(),
                "Test Product",
                "Test Description",
                new BigDecimal("9.99"),
                category,
                List.of()
        );

        // When
        ProductResponse response = GetProductBase.makeProductResponse(product);

        // Then
        assertEquals(category, response.getCategory());
    }

    @Test
    @DisplayName("Should handle null description correctly")
    void makeProductResponse_withNullDescription_shouldReturnNull() {
        // Given
        Product product = Product.ProductBuilder.aProduct()
                .withId(new ProductId(UUID.randomUUID()))
                .withName("Mystery Product")
                .withDescription(null)
                .withPrice(new Money(new BigDecimal("5.99")))
                .withCategory(ProductCategory.LANCHE)
                .withImages(List.of())
                .build();

        // When
        ProductResponse response = GetProductBase.makeProductResponse(product);

        // Then
        assertNull(response.getDescription());
    }
}