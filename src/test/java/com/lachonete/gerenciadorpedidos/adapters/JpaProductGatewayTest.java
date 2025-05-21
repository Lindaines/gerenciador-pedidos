package com.lachonete.gerenciadorpedidos.adapters;

import com.lachonete.gerenciadorpedidos.adapters.data.ProductData;
import com.lachonete.gerenciadorpedidos.adapters.repositories.ProductRepository;
import com.lachonete.gerenciadorpedidos.entities.Product;
import com.lachonete.gerenciadorpedidos.entities.valueobject.Image;
import com.lachonete.gerenciadorpedidos.entities.valueobject.Money;
import com.lachonete.gerenciadorpedidos.entities.valueobject.ProductCategory;
import com.lachonete.gerenciadorpedidos.entities.valueobject.ProductId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JpaProductGatewayTest {

    private ProductRepository productRepository;
    private JpaProductGateway jpaProductGateway;

    @BeforeEach
    void setUp() {
        productRepository = mock(ProductRepository.class);
        jpaProductGateway = new JpaProductGateway(productRepository);
    }

    // Helper method to create a ProductData instance
    private ProductData createProductData(UUID id, String name, String desc, ProductCategory category, BigDecimal price, List<String> images) {
        return ProductData.builder()
                .id(id)
                .name(name)
                .description(desc)
                .category(category)
                .price(price)
                .images(images)
                .build();
    }

    // Helper method to create a Product instance
    private Product createProduct(UUID id, String name, String desc, ProductCategory category, BigDecimal price, List<String> imageUrls) {
        List<Image> images = imageUrls != null ? imageUrls.stream().map(Image::new).toList() : Collections.emptyList();
        return Product.ProductBuilder.aProduct()
                .withId(new ProductId(id))
                .withName(name)
                .withDescription(desc)
                .withCategory(category)
                .withPrice(new Money(price))
                .withImages(images)
                .build();
    }

    // --- Tests for getAll ---

    @Test
    void getAll_ShouldReturnListOfProductsWhenRepositoryHasData() {
        // Given
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        ProductData data1 = createProductData(id1, "Burger", "Delicious", ProductCategory.LANCHE, new BigDecimal("15.99"), Arrays.asList("url1.png", "url2.jpg"));
        ProductData data2 = createProductData(id2, "Fries", "Crispy", ProductCategory.ACOMPANHAMENTO, new BigDecimal("5.00"), Collections.emptyList());
        List<ProductData> productDataList = Arrays.asList(data1, data2);

        when(productRepository.findAll()).thenReturn(productDataList);

        // When
        List<Product> result = jpaProductGateway.getAll();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());

        Product product1 = result.get(0);
        assertEquals(id1, product1.getId().getValue());
        assertEquals("Burger", product1.getName());
        assertEquals("Delicious", product1.getDescription());
        assertEquals(ProductCategory.LANCHE, product1.getCategory());
        assertEquals(new BigDecimal("15.99"), product1.getPrice().getAmount());
        assertEquals(2, product1.getImages().size());
        assertEquals("url1.png", product1.getImages().get(0).urlPath);

        Product product2 = result.get(1);
        assertEquals(id2, product2.getId().getValue());
        assertEquals("Fries", product2.getName());
        assertEquals("Crispy", product2.getDescription());
        assertEquals(ProductCategory.ACOMPANHAMENTO, product2.getCategory());
        assertEquals(new BigDecimal("5.00"), product2.getPrice().getAmount());
        assertTrue(product2.getImages().isEmpty());

        verify(productRepository, times(1)).findAll();
    }

    @Test
    void getAll_ShouldReturnEmptyListWhenRepositoryIsEmpty() {
        // Given
        when(productRepository.findAll()).thenReturn(Collections.emptyList());

        // When
        List<Product> result = jpaProductGateway.getAll();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(productRepository, times(1)).findAll();
    }

    // --- Tests for add ---

    @Test
    void add_ShouldSaveProductDataAndReturnNewProductId() {
        // Given
        Product productToAdd = createProduct(null, "New Pizza", "Pepperoni", ProductCategory.LANCHE, new BigDecimal("35.00"), Arrays.asList("pizza.jpg"));

        // Use ArgumentCaptor to capture the ProductData saved
        ArgumentCaptor<ProductData> productDataCaptor = ArgumentCaptor.forClass(ProductData.class);
        when(productRepository.save(productDataCaptor.capture())).thenReturn(null); // save returns the entity, but null is fine for verification

        // When
        ProductId returnedId = jpaProductGateway.add(productToAdd);

        // Then
        assertNotNull(returnedId);
        assertNotNull(returnedId.getValue()); // ID should be generated

        ProductData capturedProductData = productDataCaptor.getValue();
        assertNotNull(capturedProductData);
        assertEquals(returnedId.getValue(), capturedProductData.getId()); // Generated ID should be set in ProductData
        assertEquals(productToAdd.getName(), capturedProductData.getName());
        assertEquals(productToAdd.getDescription(), capturedProductData.getDescription());
        assertEquals(productToAdd.getCategory(), capturedProductData.getCategory());
        assertEquals(productToAdd.getPrice().getAmount(), capturedProductData.getPrice());
        assertEquals(1, capturedProductData.getImages().size());
        assertEquals("pizza.jpg", capturedProductData.getImages().get(0));

        verify(productRepository, times(1)).save(any(ProductData.class));
    }

    // --- Tests for getById ---

    @Test
    void getById_ShouldReturnProductWhenFound() {
        // Given
        UUID productId = UUID.randomUUID();
        ProductData foundData = createProductData(productId, "Soda", "Refreshing", ProductCategory.BEBIDA, new BigDecimal("7.50"), Collections.emptyList());

        when(productRepository.findById(productId)).thenReturn(Optional.of(foundData));

        // When
        Product result = jpaProductGateway.getById(productId);

        // Then
        assertNotNull(result);
        assertEquals(productId, result.getId().getValue());
        assertEquals("Soda", result.getName());
        assertEquals("Refreshing", result.getDescription());
        assertEquals(ProductCategory.BEBIDA, result.getCategory());
        assertEquals(new BigDecimal("7.50"), result.getPrice().getAmount());
        assertTrue(result.getImages().isEmpty());
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void getById_ShouldReturnNullWhenNotFound() {
        // Given
        UUID productId = UUID.randomUUID();
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        // When
        Product result = jpaProductGateway.getById(productId);

        // Then
        assertNull(result);
        verify(productRepository, times(1)).findById(productId);
    }

    // --- Tests for update ---


    // --- Tests for delete ---

    @Test
    void delete_ShouldCallRepositoryDeleteById() {
        // Given
        UUID productIdToDelete = UUID.randomUUID();

        // When
        jpaProductGateway.delete(productIdToDelete);

        // Then
        verify(productRepository, times(1)).deleteById(productIdToDelete);
    }
}