package com.lachonete.gerenciadorpedidos.presenters.product;

import com.lachonete.gerenciadorpedidos.ports.presenters.product.ProductCreatedViewModel;
import com.lachonete.gerenciadorpedidos.ports.usescases.product.add.NewProductResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ProductCreatedPresenterTest {

    private ProductCreatedPresenter presenter;

    @BeforeEach
    void setUp() {
        presenter = new ProductCreatedPresenter();
    }

    @Test
    void present_shouldCreateViewModelWithCorrectId() {
        // Given
        UUID productId = UUID.randomUUID();
        NewProductResponse response = new NewProductResponse(productId);

        // When
        presenter.present(response);
        ProductCreatedViewModel viewModel = presenter.getViewModel();

        // Then
        assertNotNull(viewModel);
        assertEquals(productId.toString(), viewModel.getId());
    }

    @Test
    void getViewModel_beforePresentation_shouldReturnNull() {
        assertNull(presenter.getViewModel());
    }

    @Test
    void present_withNullResponse_shouldThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> presenter.present(null));
    }

    @Test
    void present_withNullId_shouldThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> presenter.present(new NewProductResponse(null)));
    }

    @Test
    void present_shouldStoreViewModelForLaterRetrieval() {
        // Given
        UUID productId = UUID.randomUUID();
        NewProductResponse response = new NewProductResponse(productId);

        // When
        presenter.present(response);
        ProductCreatedViewModel viewModel = presenter.getViewModel();

        // Then
        assertNotNull(viewModel);
        assertEquals(productId.toString(), viewModel.getId());
    }

    @Test
    void present_multipleCalls_shouldUpdateViewModel() {
        // Given
        UUID firstId = UUID.randomUUID();
        UUID secondId = UUID.randomUUID();

        // When
        presenter.present(new NewProductResponse(firstId));
        ProductCreatedViewModel firstViewModel = presenter.getViewModel();

        presenter.present(new NewProductResponse(secondId));
        ProductCreatedViewModel secondViewModel = presenter.getViewModel();

        // Then
        assertNotEquals(firstViewModel.getId(), secondViewModel.getId());
        assertEquals(secondId.toString(), presenter.getViewModel().getId());
    }

    @Test
    void present_withDifferentIds_shouldUpdateViewModelCorrectly() {
        // Given
        UUID firstId = UUID.fromString("00000000-0000-0000-0000-000000000001");
        UUID secondId = UUID.fromString("00000000-0000-0000-0000-000000000002");

        // When
        presenter.present(new NewProductResponse(firstId));
        ProductCreatedViewModel firstViewModel = presenter.getViewModel();

        presenter.present(new NewProductResponse(secondId));
        ProductCreatedViewModel secondViewModel = presenter.getViewModel();

        // Then
        assertEquals("00000000-0000-0000-0000-000000000001", firstViewModel.getId());
        assertEquals("00000000-0000-0000-0000-000000000002", secondViewModel.getId());
    }
}