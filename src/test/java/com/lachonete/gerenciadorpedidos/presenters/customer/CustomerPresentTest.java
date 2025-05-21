package com.lachonete.gerenciadorpedidos.presenters.customer;

import com.lachonete.gerenciadorpedidos.ports.presenters.customer.CustomerViewModel;
import com.lachonete.gerenciadorpedidos.ports.usescases.customer.get.CustomerResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CustomerPresenterTest {

    private CustomerPresenter presenter;

    @BeforeEach
    void setUp() {
        presenter = new CustomerPresenter();
    }

    @Test
    void present_shouldCreateViewModelFromResponse() {
        // Given
        UUID customerId = UUID.randomUUID();
        CustomerResponse response = CustomerResponse.builder()
                .id(customerId)
                .name("John Doe")
                .cpf("123.456.789-00")
                .email("john@example.com")
                .build();

        // When
        presenter.present(response);
        CustomerViewModel viewModel = presenter.getViewModel();

        // Then
        assertNotNull(viewModel);
        assertEquals(customerId, viewModel.getId());
        assertEquals("John Doe", viewModel.getName());
        assertEquals("123.456.789-00", viewModel.getCpf());
        assertEquals("john@example.com", viewModel.getEmail());
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
    void present_shouldUpdateViewModel() {
        // Given
        UUID firstId = UUID.randomUUID();
        CustomerResponse firstResponse = CustomerResponse.builder()
                .id(firstId)
                .name("First Customer")
                .build();

        UUID secondId = UUID.randomUUID();
        CustomerResponse secondResponse = CustomerResponse.builder()
                .id(secondId)
                .name("Second Customer")
                .build();

        // When
        presenter.present(firstResponse);
        CustomerViewModel firstViewModel = presenter.getViewModel();

        presenter.present(secondResponse);
        CustomerViewModel secondViewModel = presenter.getViewModel();

        // Then
        assertNotEquals(firstViewModel.getId(), secondViewModel.getId());
        assertEquals("First Customer", firstViewModel.getName());
        assertEquals("Second Customer", secondViewModel.getName());
    }

    @Test
    void present_withPartialResponse_shouldMapAvailableFields() {
        // Given
        CustomerResponse response = CustomerResponse.builder()
                .id(UUID.randomUUID())
                .name("Partial Customer")
                // cpf and email omitted
                .build();

        // When
        presenter.present(response);
        CustomerViewModel viewModel = presenter.getViewModel();

        // Then
        assertNotNull(viewModel);
        assertEquals("Partial Customer", viewModel.getName());
        assertNull(viewModel.getCpf());
        assertNull(viewModel.getEmail());
    }

    @Test
    void present_withNullFields_shouldHandleGracefully() {
        // Given
        CustomerResponse response = CustomerResponse.builder()
                .id(null)
                .name(null)
                .cpf(null)
                .email(null)
                .build();

        // When
        presenter.present(response);
        CustomerViewModel viewModel = presenter.getViewModel();

        // Then
        assertNotNull(viewModel);
        assertNull(viewModel.getId());
        assertNull(viewModel.getName());
        assertNull(viewModel.getCpf());
        assertNull(viewModel.getEmail());
    }
}