package com.lachonete.gerenciadorpedidos.presenters.customer;

import com.lachonete.gerenciadorpedidos.ports.presenters.customer.CustomerCreatedViewModel;
import com.lachonete.gerenciadorpedidos.ports.usescases.customer.add.NewCustomerResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CustomerCreatedPresenterTest {

    private CustomerCreatedPresenter presenter;

    @BeforeEach
    void setUp() {
        presenter = new CustomerCreatedPresenter();
    }

    @Test
    void present_shouldCreateViewModelWithCorrectId() {
        // Given
        UUID customerId = UUID.randomUUID();
        NewCustomerResponse response = new NewCustomerResponse(customerId);

        // When
        presenter.present(response);

        // Then
        CustomerCreatedViewModel viewModel = presenter.getViewModel();
        assertNotNull(viewModel);
        assertEquals(customerId.toString(), viewModel.getId());
    }

    @Test
    void present_withDifferentIds_shouldUpdateViewModel() {
        // Given
        UUID firstId = UUID.randomUUID();
        UUID secondId = UUID.randomUUID();

        // When
        presenter.present(new NewCustomerResponse(firstId));
        CustomerCreatedViewModel firstViewModel = presenter.getViewModel();

        presenter.present(new NewCustomerResponse(secondId));
        CustomerCreatedViewModel secondViewModel = presenter.getViewModel();

        // Then
        assertEquals(firstId.toString(), firstViewModel.getId());
        assertEquals(secondId.toString(), secondViewModel.getId());
        assertNotEquals(firstViewModel.getId(), secondViewModel.getId());
    }

    @Test
    void present_withNullResponse_shouldThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> presenter.present(null));
    }

    @Test
    void present_withNullId_shouldThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> presenter.present(new NewCustomerResponse(null)));
    }

    @Test
    void getViewModel_beforePresentation_shouldReturnNull() {
        assertNull(presenter.getViewModel());
    }

    @Test
    void present_shouldStoreViewModelForLaterRetrieval() {
        // Given
        UUID customerId = UUID.randomUUID();
        NewCustomerResponse response = new NewCustomerResponse(customerId);

        // When
        presenter.present(response);
        CustomerCreatedViewModel viewModel = presenter.getViewModel();

        // Then
        assertNotNull(viewModel);
        assertEquals(customerId.toString(), viewModel.getId());
    }

    @Test
    void present_multipleCalls_shouldUpdateViewModel() {
        // Given
        UUID firstId = UUID.randomUUID();
        UUID secondId = UUID.randomUUID();

        // When
        presenter.present(new NewCustomerResponse(firstId));
        CustomerCreatedViewModel firstViewModel = presenter.getViewModel();

        presenter.present(new NewCustomerResponse(secondId));
        CustomerCreatedViewModel secondViewModel = presenter.getViewModel();

        // Then
        assertNotEquals(firstViewModel.getId(), secondViewModel.getId());
        assertEquals(secondId.toString(), presenter.getViewModel().getId());
    }
}