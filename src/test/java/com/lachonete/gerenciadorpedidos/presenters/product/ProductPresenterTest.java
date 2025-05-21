package com.lachonete.gerenciadorpedidos.presenters.product;

import com.lachonete.gerenciadorpedidos.entities.valueobject.ProductCategory;
import com.lachonete.gerenciadorpedidos.ports.presenters.product.ProductViewModel;
import com.lachonete.gerenciadorpedidos.ports.usescases.product.get.ProductResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ProductPresenterTest {

    private ProductPresenter productPresenter;

    @BeforeEach
    void setUp() {
        // Initialize a new ProductPresenter before each test to ensure a clean state
        productPresenter = new ProductPresenter();
    }

    @Test
    void present_ShouldMapProductResponseToProductViewModelCorrectly() {
        // Given: A ProductResponse object with sample data
        UUID productId = UUID.randomUUID();
        String productName = "Test Product";
        String productDescription = "This is a test product description.";
        BigDecimal productPrice = new BigDecimal("99.99");
        var productCategory = ProductCategory.LANCHE;

        ProductResponse productResponse = ProductResponse.builder()
                .id(productId)
                .name(productName)
                .description(productDescription)
                .price(productPrice)
                .category(productCategory)
                .build();

        // When: The present method is called
        productPresenter.present(productResponse);

        // Then: The viewModel should be correctly populated
        ProductViewModel viewModel = productPresenter.getViewModel();

        assertNotNull(viewModel, "ViewModel should not be null after presentation.");
        assertEquals(productId.toString(), viewModel.getId(), "Product ID should match.");
        assertEquals(productName, viewModel.getName(), "Product name should match.");
        assertEquals(productDescription, viewModel.getDescription(), "Product description should match.");
        assertEquals(productPrice, viewModel.getPrice(), "Product price should match.");
        assertEquals(productCategory, viewModel.getCategory(), "Product category should match.");
    }


    @Test
    void getViewModel_ShouldReturnNullInitially() {
        // When: The getViewModel method is called before any presentation
        ProductViewModel viewModel = productPresenter.getViewModel();

        // Then: The viewModel should be null
        assertNull(viewModel, "ViewModel should be null initially before any presentation.");
    }

    @Test
    void present_ShouldOverwritePreviousViewModel() {
        // Given: A first ProductResponse and a second distinct ProductResponse
        ProductResponse firstResponse = ProductResponse.builder()
                .id(UUID.randomUUID())
                .name("First Product")
                .price(new BigDecimal("10.00"))
                .category(ProductCategory.BEBIDA)
                .build();

        ProductResponse secondResponse = ProductResponse.builder()
                .id(UUID.randomUUID())
                .name("Second Product")
                .price(new BigDecimal("20.00"))
                .category(ProductCategory.LANCHE)
                .build();

        // When: Presenting the first response
        productPresenter.present(firstResponse);
        ProductViewModel firstViewModel = productPresenter.getViewModel();

        // And then presenting the second response
        productPresenter.present(secondResponse);
        ProductViewModel secondViewModel = productPresenter.getViewModel();

        // Then: The view model should reflect the data from the second presentation
        assertNotEquals(firstViewModel.getId(), secondViewModel.getId(), "ViewModel should be updated with new ID.");
        assertEquals(secondResponse.getName(), secondViewModel.getName(), "ViewModel should be updated with new name.");
    }
}