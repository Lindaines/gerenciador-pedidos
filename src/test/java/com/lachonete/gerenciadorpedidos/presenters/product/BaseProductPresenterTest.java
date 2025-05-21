package com.lachonete.gerenciadorpedidos.presenters.product;

import com.lachonete.gerenciadorpedidos.entities.valueobject.ProductCategory;
import com.lachonete.gerenciadorpedidos.ports.presenters.product.ProductViewModel;
import com.lachonete.gerenciadorpedidos.ports.usescases.product.get.ProductResponse;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class BaseProductPresenterTest {

    @Test
    void mapToProductViewModel_shouldMapAllFieldsCorrectly() {
        // Given
        UUID productId = UUID.randomUUID();
        ProductResponse response = ProductResponse.builder()
                .id(productId)
                .name("Cheeseburger")
                .description("Delicious cheeseburger with cheddar")
                .category(ProductCategory.LANCHE)
                .price(new BigDecimal("12.99"))
                .images(List.of("burger.jpg", "cheese.jpg"))
                .build();

        // When
        ProductViewModel viewModel = BaseProductPresenter.mapToProductViewModel(response);

        // Then
        assertNotNull(viewModel);
        assertEquals(productId.toString(), viewModel.getId());
        assertEquals("Cheeseburger", viewModel.getName());
        assertEquals("Delicious cheeseburger with cheddar", viewModel.getDescription());
        assertEquals(ProductCategory.LANCHE, viewModel.getCategory());
        assertEquals(new BigDecimal("12.99"), viewModel.getPrice());
        assertEquals(2, viewModel.getImages().size());
        assertEquals("burger.jpg", viewModel.getImages().get(0));
        assertEquals("cheese.jpg", viewModel.getImages().get(1));
    }

    @Test
    void mapToProductViewModel_withNullResponse_shouldThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> BaseProductPresenter.mapToProductViewModel(null));
    }


    @Test
    void mapToProductViewModel_withEmptyImages_shouldReturnEmptyList() {
        // Given
        ProductResponse response = ProductResponse.builder()
                .id(UUID.randomUUID())
                .name("Salad")
                .images(List.of())
                .build();

        // When
        ProductViewModel viewModel = BaseProductPresenter.mapToProductViewModel(response);

        // Then
        assertNotNull(viewModel);
        assertTrue(viewModel.getImages().isEmpty());
    }

    @Test
    void mapToProductViewModel_withSpecialCharacters_shouldPreserveThem() {
        // Given
        ProductResponse response = ProductResponse.builder()
                .id(UUID.randomUUID())
                .name("Pão de Queijo")
                .description("Delicioso pão de queijo mineiro")
                .category(ProductCategory.LANCHE)
                .price(new BigDecimal("5.99"))
                .images(List.of("pão.jpg"))
                .build();

        // When
        ProductViewModel viewModel = BaseProductPresenter.mapToProductViewModel(response);

        // Then
        assertEquals("Pão de Queijo", viewModel.getName());
        assertEquals("Delicioso pão de queijo mineiro", viewModel.getDescription());
        assertEquals("pão.jpg", viewModel.getImages().get(0));
    }

    @Test
    void mapToProductViewModel_withZeroPrice_shouldMapCorrectly() {
        // Given
        ProductResponse response = ProductResponse.builder()
                .id(UUID.randomUUID())
                .name("Free Sample")
                .price(BigDecimal.ZERO)
                .build();

        // When
        ProductViewModel viewModel = BaseProductPresenter.mapToProductViewModel(response);

        // Then
        assertEquals(BigDecimal.ZERO, viewModel.getPrice());
    }

    @Test
    void mapToProductViewModel_withAllProductCategories_shouldMapCorrectly() {
        // Given
        ProductResponse lancheResponse = ProductResponse.builder()
                .id(UUID.randomUUID())
                .name("Burger")
                .category(ProductCategory.LANCHE)
                .build();

        ProductResponse bebidaResponse = ProductResponse.builder()
                .id(UUID.randomUUID())
                .name("Soda")
                .category(ProductCategory.BEBIDA)
                .build();

        // When
        ProductViewModel lancheViewModel = BaseProductPresenter.mapToProductViewModel(lancheResponse);
        ProductViewModel bebidaViewModel = BaseProductPresenter.mapToProductViewModel(bebidaResponse);

        // Then
        assertEquals(ProductCategory.LANCHE, lancheViewModel.getCategory());
        assertEquals(ProductCategory.BEBIDA, bebidaViewModel.getCategory());
    }
}