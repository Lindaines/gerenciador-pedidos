package com.lachonete.gerenciadorpedidos.presenters.product;

import com.lachonete.gerenciadorpedidos.entities.valueobject.ProductCategory;
import com.lachonete.gerenciadorpedidos.ports.presenters.product.ProductViewModel;
import com.lachonete.gerenciadorpedidos.ports.presenters.product.ProductsViewModel;
import com.lachonete.gerenciadorpedidos.ports.usescases.product.get.ProductResponse;
import com.lachonete.gerenciadorpedidos.ports.usescases.product.get.ProductsResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ProductsPresenterTest {

    private ProductsPresenter productsPresenter;

    @BeforeEach
    void setUp() {
        // Initialize a new ProductsPresenter before each test to ensure a clean state
        productsPresenter = new ProductsPresenter();
    }

    @Test
    void present_ShouldMapProductsResponseToProductsViewModelCorrectly() {
        // Given: A ProductsResponse object with a list of ProductResponse
        ProductResponse product1 = ProductResponse.builder()
                .id(UUID.randomUUID())
                .name("Product A")
                .description("Description A")
                .price(new BigDecimal("10.00"))
                .category(ProductCategory.LANCHE)
                .build();

        ProductResponse product2 = ProductResponse.builder()
                .id(UUID.randomUUID())
                .name("Product B")
                .description("Description B")
                .price(new BigDecimal("25.50"))
                .category(ProductCategory.BEBIDA)
                .build();

        List<ProductResponse> productResponses = Arrays.asList(product1, product2);
        ProductsResponse productsResponse = ProductsResponse.builder()
                .products(productResponses)
                .build();

        // When: The present method is called
        productsPresenter.present(productsResponse);

        // Then: The viewModel should be correctly populated with mapped products
        ProductsViewModel viewModel = productsPresenter.getViewModel();

        assertNotNull(viewModel, "ViewModel should not be null after presentation.");
        // Corrected: Use getProducts() to access the list
        assertNotNull(viewModel.getProducts(), "List of ProductViewModel should not be null.");
        assertEquals(2, viewModel.getProducts().size(), "There should be 2 products in the view model.");

        // Verify mapping for product 1
        ProductViewModel vm1 = viewModel.getProducts().get(0); // Corrected: Use getProducts()
        assertEquals(product1.getId().toString(), vm1.getId(), "Product 1 ID should match.");
        assertEquals(product1.getName(), vm1.getName(), "Product 1 name should match.");
        assertEquals(product1.getDescription(), vm1.getDescription(), "Product 1 description should match.");
        assertEquals(product1.getPrice(), vm1.getPrice(), "Product 1 price should match.");
        assertEquals(product1.getCategory(), vm1.getCategory(), "Product 1 category should match.");

        // Verify mapping for product 2
        ProductViewModel vm2 = viewModel.getProducts().get(1); // Corrected: Use getProducts()
        assertEquals(product2.getId().toString(), vm2.getId(), "Product 2 ID should match.");
        assertEquals(product2.getName(), vm2.getName(), "Product 2 name should match.");
        assertEquals(product2.getDescription(), vm2.getDescription(), "Product 2 description should match.");
        assertEquals(product2.getPrice(), vm2.getPrice(), "Product 2 price should match.");
        assertEquals(product2.getCategory(), vm2.getCategory(), "Product 2 category should match.");
    }

    @Test
    void present_ShouldHandleEmptyProductsResponse() {
        // Given: An empty ProductsResponse
        ProductsResponse productsResponse = ProductsResponse.builder()
                .products(Collections.emptyList())
                .build();

        // When: The present method is called
        productsPresenter.present(productsResponse);

        // Then: The viewModel should contain an empty list of products
        ProductsViewModel viewModel = productsPresenter.getViewModel();

        assertNotNull(viewModel, "ViewModel should not be null.");
        // Corrected: Use getProducts() to access the list
        assertNotNull(viewModel.getProducts(), "List of ProductViewModel should not be null.");
        assertTrue(viewModel.getProducts().isEmpty(), "The list of products should be empty."); // Corrected: Use getProducts()
    }

    @Test
    void getViewModel_ShouldReturnNullInitially() {
        // When: The getViewModel method is called before any presentation
        ProductsViewModel viewModel = productsPresenter.getViewModel();

        // Then: The viewModel should be null
        assertNull(viewModel, "ViewModel should be null initially before any presentation.");
    }

    @Test
    void present_ShouldOverwritePreviousViewModel() {
        // Given: A first ProductsResponse and a second distinct ProductsResponse
        ProductResponse firstProduct = ProductResponse.builder()
                .id(UUID.randomUUID())
                .name("Initial Product")
                .price(new BigDecimal("10.00"))
                .category(ProductCategory.ACOMPANHAMENTO)
                .build();
        ProductsResponse firstResponse = ProductsResponse.builder()
                .products(Collections.singletonList(firstProduct))
                .build();

        ProductResponse secondProduct = ProductResponse.builder()
                .id(UUID.randomUUID())
                .name("New Product")
                .price(new BigDecimal("25.00"))
                .category(ProductCategory.BEBIDA)
                .build();
        ProductsResponse secondResponse = ProductsResponse.builder()
                .products(Collections.singletonList(secondProduct))
                .build();

        // When: Presenting the first response
        productsPresenter.present(firstResponse);
        ProductsViewModel firstViewModel = productsPresenter.getViewModel();
        // Corrected: Use getProducts()
        assertEquals(1, firstViewModel.getProducts().size(), "First view model should have one product.");
        assertEquals("Initial Product", firstViewModel.getProducts().get(0).getName());

        // And then presenting the second response
        productsPresenter.present(secondResponse);
        ProductsViewModel secondViewModel = productsPresenter.getViewModel();

        // Then: The view model should reflect the data from the second presentation
        assertNotNull(secondViewModel, "Second view model should not be null.");
        // Corrected: Use getProducts()
        assertEquals(1, secondViewModel.getProducts().size(), "Second view model should have one product.");
        assertEquals(secondProduct.getId().toString(), secondViewModel.getProducts().get(0).getId(), "Product ID should be updated.");
        assertEquals(secondProduct.getName(), secondViewModel.getProducts().get(0).getName(), "Product name should be updated.");
        assertEquals(secondProduct.getCategory(), secondViewModel.getProducts().get(0).getCategory(), "Product category should be updated.");
    }
}