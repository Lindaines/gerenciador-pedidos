package com.lachonete.gerenciadorpedidos.usecases.product.add;

import com.lachonete.gerenciadorpedidos.entities.Product;
import com.lachonete.gerenciadorpedidos.entities.valueobject.ProductCategory;
import com.lachonete.gerenciadorpedidos.entities.valueobject.ProductId;
import com.lachonete.gerenciadorpedidos.ports.database.ProductGateway;
import com.lachonete.gerenciadorpedidos.ports.presenters.product.ProductCreatedOutputBoundary;
import com.lachonete.gerenciadorpedidos.ports.usescases.product.add.AddProductRequest;
import com.lachonete.gerenciadorpedidos.ports.usescases.product.add.NewProductResponse;
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
class AddProductTest {

    @Mock
    private ProductCreatedOutputBoundary presenter;

    @Mock
    private ProductGateway productGateway;

    @Captor
    private ArgumentCaptor<NewProductResponse> responseCaptor;

    private AddProduct addProduct;

    @BeforeEach
    void setUp() {
        addProduct = new AddProduct(presenter, productGateway);
    }

    @Test
    void execute_shouldAddProductAndPresentResponse() {
        // Given
        var expectedId = new ProductId(UUID.fromString("d3553fca-aaea-4574-8111-d8fa0c4ed4c7"));
        AddProductRequest request = createValidRequest();

        when(productGateway.add(any(Product.class))).thenReturn(Product.ProductBuilder.aProduct().
                withId(expectedId).build().getId());
        // When
        addProduct.execute(request);

        // Then
        verify(productGateway).add(argThat(product ->
                product.getName().equals("Burger") &&
                        product.getPrice().getAmount().equals(new BigDecimal("10.99")) &&
                        product.getImages().size() == 1
        ));

        verify(presenter).present(responseCaptor.capture());
        NewProductResponse response = responseCaptor.getValue();
        assertEquals(expectedId.getValue(), response.getId());
    }

    @Test
    void execute_withNullRequest_shouldThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> addProduct.execute(null));
    }
    private AddProductRequest createValidRequest() {
        return new AddProductRequest(
                "Burger",
                "Fast Food",
                new BigDecimal("10.99"),
                ProductCategory.LANCHE,
                List.of("burger.jpg")
        );
    }
}