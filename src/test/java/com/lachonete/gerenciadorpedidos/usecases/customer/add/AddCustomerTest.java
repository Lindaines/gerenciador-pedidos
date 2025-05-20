package com.lachonete.gerenciadorpedidos.usecases.customer.add;

import com.lachonete.gerenciadorpedidos.entities.Customer;
import com.lachonete.gerenciadorpedidos.entities.valueobject.*;
import com.lachonete.gerenciadorpedidos.ports.database.CustomerGateway;
import com.lachonete.gerenciadorpedidos.ports.presenters.customer.CustomerCreatedOutputBoundary;
import com.lachonete.gerenciadorpedidos.ports.usescases.customer.add.AddCustomerRequest;
import com.lachonete.gerenciadorpedidos.ports.usescases.customer.add.NewCustomerResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddCustomerTest {

    @Mock
    private CustomerGateway customerGateway;

    @Mock
    private CustomerCreatedOutputBoundary presenter;

    private AddCustomer addCustomer;
    private final UUID expectedCustomerId = UUID.randomUUID();
    private final AddCustomerRequest validRequest = new AddCustomerRequest(
            "123.456.789-09",
            "customer@test.com",
            "John Doe"
    );

    @BeforeEach
    void setUp() {
        addCustomer = new AddCustomer(presenter, customerGateway);
    }

    @Test
    void shouldAddNewCustomerWhenValidRequest() {
        // Arrange
        when(customerGateway.add(any(Customer.class)))
                .thenReturn(new CustomerId(expectedCustomerId));

        // Act
        addCustomer.execute(validRequest);

        // Assert
        verifyCustomerWasCreatedWithCorrectParameters();
        verifyResponseWasPresented();
    }

    @Test
    void shouldCreateCustomerWithCorrectParameters() {
        // Arrange
        when(customerGateway.add(any(Customer.class)))
                .thenReturn(new CustomerId(expectedCustomerId));

        // Act
        addCustomer.execute(validRequest);

        // Assert
        ArgumentCaptor<Customer> customerCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerGateway).add(customerCaptor.capture());

        Customer createdCustomer = customerCaptor.getValue();
        assertAll(
                () -> assertEquals(validRequest.getCpf(), createdCustomer.getCpf().toString()),
                () -> assertEquals(validRequest.getEmail(), createdCustomer.getEmail().toString()),
                () -> assertEquals(validRequest.getName(), createdCustomer.getName().toString())
        );
    }

    @Test
    void shouldPresentResponseWithCorrectId() {
        // Arrange
        when(customerGateway.add(any(Customer.class)))
                .thenReturn(new CustomerId(expectedCustomerId));

        // Act
        addCustomer.execute(validRequest);

        // Assert
        ArgumentCaptor<NewCustomerResponse> responseCaptor =
                ArgumentCaptor.forClass(NewCustomerResponse.class);
        verify(presenter).present(responseCaptor.capture());

        assertEquals(expectedCustomerId, responseCaptor.getValue().getId());
    }

    @Test
    void shouldHandleNullRequestGracefully() {
        assertThrows(
                NullPointerException.class,
                () -> addCustomer.execute(null)
        );
    }

    private void verifyCustomerWasCreatedWithCorrectParameters() {
        ArgumentCaptor<Customer> customerCaptor = ArgumentCaptor.forClass(Customer.class);
        verify(customerGateway).add(customerCaptor.capture());

        Customer createdCustomer = customerCaptor.getValue();
        assertAll(
                () -> assertEquals(validRequest.getCpf(), createdCustomer.getCpf().toString()),
                () -> assertEquals(validRequest.getEmail(), createdCustomer.getEmail().toString()),
                () -> assertEquals(validRequest.getName(), createdCustomer.getName().toString())
        );
    }

    private void verifyResponseWasPresented() {
        ArgumentCaptor<NewCustomerResponse> responseCaptor =
                ArgumentCaptor.forClass(NewCustomerResponse.class);
        verify(presenter).present(responseCaptor.capture());

        NewCustomerResponse response = responseCaptor.getValue();
        assertEquals(expectedCustomerId, response.getId());
    }
}