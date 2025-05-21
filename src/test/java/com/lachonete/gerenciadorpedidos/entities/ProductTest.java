package com.lachonete.gerenciadorpedidos.entities;

import com.lachonete.gerenciadorpedidos.entities.valueobject.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class ProductTest {
    private final ProductId testId = new ProductId(UUID.randomUUID());
    private final String testName = "Cheeseburger";
    private final String testDescription = "Delicious beef patty with cheese";
    private final Money testPrice = new Money(new BigDecimal("9.99"));
    private final ProductCategory testCategory = ProductCategory.BEBIDA;
    private final List<Image> testImages = List.of(
            new Image("burger1.jpg"),
            new Image("burger2.jpg")
    );

    @Test
    void shouldBuildProductWithAllFields() {
        Product product = Product.ProductBuilder.aProduct()
                .withId(testId)
                .withName(testName)
                .withDescription(testDescription)
                .withPrice(testPrice)
                .withCategory(testCategory)
                .withImages(testImages)
                .build();

        assertAll(
                () -> assertThat(product.getId()).isEqualTo(testId),
                () -> assertThat(product.getName()).isEqualTo(testName),
                () -> assertThat(product.getDescription()).isEqualTo(testDescription),
                () -> assertThat(product.getPrice()).isEqualTo(testPrice),
                () -> assertThat(product.getCategory()).isEqualTo(testCategory),
                () -> assertThat(product.getImages()).containsExactlyElementsOf(testImages)
        );
    }

    @Test
    void shouldCreateEmptyProduct() {
        Product product = new Product();

        assertAll(
                () -> assertNull(product.getId()),
                () -> assertNull(product.getName()),
                () -> assertNull(product.getDescription()),
                () -> assertNull(product.getPrice()),
                () -> assertNull(product.getCategory()),
                () -> assertNull(product.getImages())
        );
    }

    @Test
    void shouldCreateProductWithIdOnly() {
        Product product = new Product(testId);

        assertEquals(testId, product.getId());
        assertNull(product.getName());
    }

    @Test
    void shouldUpdateAllFieldsViaSetters() {
        Product product = new Product();

        product.setName(testName);
        product.setDescription(testDescription);
        product.setPrice(testPrice);
        product.setCategory(testCategory);
        product.setImages(testImages);

        assertAll(
                () -> assertEquals(testName, product.getName()),
                () -> assertEquals(testDescription, product.getDescription()),
                () -> assertEquals(testPrice, product.getPrice()),
                () -> assertEquals(testCategory, product.getCategory()),
                () -> assertEquals(testImages, product.getImages())
        );
    }

    @ParameterizedTest
    @NullAndEmptySource
    void shouldHandleNullOrEmptyName(String name) {
        Product product = new Product();
        product.setName(name);
        assertEquals(name, product.getName());
    }

    @Test
    void equalsAndHashCode_ShouldDependOnlyOnId() {
        Product product1 = Product.ProductBuilder.aProduct()
                .withId(testId)
                .withName("Product A")
                .build();

        Product product2 = Product.ProductBuilder.aProduct()
                .withId(testId)
                .withName("Product B") // Different name, same ID
                .build();

        Product differentProduct = Product.ProductBuilder.aProduct()
                .withId(new ProductId(UUID.randomUUID()))
                .build();

        assertAll(
                () -> assertThat(product1).isEqualTo(product2),
                () -> assertThat(product1).isNotEqualTo(differentProduct),
                () -> assertThat(product1.hashCode()).isEqualTo(product2.hashCode())
        );
    }

    @Test
    void builder_ShouldHandlePartialFields() {
        Product product = Product.ProductBuilder.aProduct()
                .withName(testName)
                .withPrice(testPrice)
                .build();

        assertAll(
                () -> assertThat(product.getName()).isEqualTo(testName),
                () -> assertThat(product.getPrice()).isEqualTo(testPrice),
                () -> assertNull(product.getId()),
                () -> assertNull(product.getDescription())
        );
    }

    @Test
    void shouldHandleNullImages() {
        Product product = Product.ProductBuilder.aProduct()
                .withImages(null)
                .build();

        assertNull(product.getImages());
    }
}