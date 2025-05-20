package com.lachonete.gerenciadorpedidos.usecases.customer.get;

import com.lachonete.gerenciadorpedidos.entities.Customer;
import com.lachonete.gerenciadorpedidos.entities.valueobject.Cpf;
import com.lachonete.gerenciadorpedidos.entities.valueobject.CustomerId;
import com.lachonete.gerenciadorpedidos.entities.valueobject.EmailAddress;
import com.lachonete.gerenciadorpedidos.entities.valueobject.PersonName;
import com.lachonete.gerenciadorpedidos.ports.database.CustomerGateway;
import com.lachonete.gerenciadorpedidos.ports.presenters.customer.CustomerOutputBoundary;
import com.lachonete.gerenciadorpedidos.ports.usescases.customer.get.CustomerResponse;
import com.lachonete.gerenciadorpedidos.ports.usescases.customer.get.GetCustomerByIdRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetCustomerByIdTest {

    private CustomerGateway customerGateway;
    private CustomerOutputBoundary presenter;
    private GetCustomerById useCase;

    @BeforeEach
    void setup() {
        customerGateway = mock(CustomerGateway.class);
        presenter = mock(CustomerOutputBoundary.class);
        useCase = new GetCustomerById(presenter, customerGateway);
    }


    @Test
    void shouldThrowExceptionWhenCustomerNotFound() {
        // Given
        when(customerGateway.getById(UUID.randomUUID())).thenReturn(null);
        GetCustomerByIdRequest request = new GetCustomerByIdRequest(UUID.randomUUID());

        // When / Then
        assertThrows(CustomerGateway.CustomerNotFoundException.class, () -> {
            useCase.execute(request);
        });
    }
}
