package com.lachonete.gerenciadorpedidos.presenters.customer;

import com.lachonete.gerenciadorpedidos.ports.presenters.customer.CustomerViewModel;
import com.lachonete.gerenciadorpedidos.ports.usescases.customer.get.CustomerResponse;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class BaseCustomerPresenterTest {

    @Test
    void mapToCustomerViewModel_shouldMapAllFieldsCorrectly() {
        // Given
        UUID customerId = UUID.randomUUID();
        CustomerResponse response = CustomerResponse.builder()
                .id(customerId)
                .name("John Doe")
                .cpf("123.456.789-00")
                .email("john.doe@example.com")
                .build();

        // When
        CustomerViewModel viewModel = BaseCustomerPresenter.mapToCustomerViewModel(response);

        // Then
        assertNotNull(viewModel);
        assertEquals(customerId, viewModel.getId());
        assertEquals("John Doe", viewModel.getName());
        assertEquals("123.456.789-00", viewModel.getCpf());
        assertEquals("john.doe@example.com", viewModel.getEmail());
    }

    @Test
    void mapToCustomerViewModel_withNullResponse_shouldThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> BaseCustomerPresenter.mapToCustomerViewModel(null));
    }

    @Test
    void mapToCustomerViewModel_withNullFields_shouldHandleGracefully() {
        // Given
        CustomerResponse response = CustomerResponse.builder()
                .id(null)
                .name(null)
                .cpf(null)
                .email(null)
                .build();

        // When
        CustomerViewModel viewModel = BaseCustomerPresenter.mapToCustomerViewModel(response);

        // Then
        assertNotNull(viewModel);
        assertNull(viewModel.getId());
        assertNull(viewModel.getName());
        assertNull(viewModel.getCpf());
        assertNull(viewModel.getEmail());
    }

    @Test
    void mapToCustomerViewModel_withEmptyStrings_shouldPreserveEmptyValues() {
        // Given
        UUID customerId = UUID.randomUUID();
        CustomerResponse response = CustomerResponse.builder()
                .id(customerId)
                .name("")
                .cpf("")
                .email("")
                .build();

        // When
        CustomerViewModel viewModel = BaseCustomerPresenter.mapToCustomerViewModel(response);

        // Then
        assertNotNull(viewModel);
        assertEquals(customerId, viewModel.getId());
        assertEquals("", viewModel.getName());
        assertEquals("", viewModel.getCpf());
        assertEquals("", viewModel.getEmail());
    }

    @Test
    void mapToCustomerViewModel_withPartialResponse_shouldMapAvailableFields() {
        // Given
        UUID customerId = UUID.randomUUID();
        CustomerResponse response = CustomerResponse.builder()
                .id(customerId)
                .name("Jane Smith")
                // cpf and email omitted
                .build();

        // When
        CustomerViewModel viewModel = BaseCustomerPresenter.mapToCustomerViewModel(response);

        // Then
        assertNotNull(viewModel);
        assertEquals(customerId, viewModel.getId());
        assertEquals("Jane Smith", viewModel.getName());
        assertNull(viewModel.getCpf());
        assertNull(viewModel.getEmail());
    }
}