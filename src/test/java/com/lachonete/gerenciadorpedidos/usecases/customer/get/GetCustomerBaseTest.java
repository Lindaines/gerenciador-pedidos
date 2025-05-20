package com.lachonete.gerenciadorpedidos.usecases.customer.get;

import com.lachonete.gerenciadorpedidos.entities.Customer;
import com.lachonete.gerenciadorpedidos.entities.valueobject.*;
import com.lachonete.gerenciadorpedidos.ports.usescases.customer.get.CustomerResponse;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class GetCustomerBaseTest {

    @Test
    void shouldCreateCustomerResponseWithAllFields() {
        // Arrange
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(
                new Cpf("123.456.789-09"),
                new EmailAddress("customer@test.com"),
                new PersonName("John Doe")
        );
        customer.setId(new CustomerId(customerId));

        // Act
        CustomerResponse response = GetCustomerBase.makeCustomerResponse(customer);

        // Assert
        assertAll(
                () -> assertEquals(customerId, response.getId()),
                () -> assertEquals("John Doe", response.getName()),
                () -> assertEquals("123.456.789-09", response.getCpf()),
                () -> assertEquals("customer@test.com", response.getEmail())
        );
    }

    @Test
    void shouldHandleNullCustomer() {
        assertThrows(
                NullPointerException.class,
                () -> GetCustomerBase.makeCustomerResponse(null)
        );
    }



    @Test
    void shouldHandleCustomerWithEmptyName() {
        // Arrange
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(
                new Cpf("123.456.789-09"),
                new EmailAddress("customer@test.com"),
                new PersonName("")
        );
        customer.setId(new CustomerId(customerId));

        // Act
        CustomerResponse response = GetCustomerBase.makeCustomerResponse(customer);

        // Assert
        assertEquals("", response.getName());
    }
}