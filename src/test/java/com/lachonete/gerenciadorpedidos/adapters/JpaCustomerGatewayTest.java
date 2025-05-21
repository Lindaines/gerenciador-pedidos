package com.lachonete.gerenciadorpedidos.adapters;

import com.lachonete.gerenciadorpedidos.adapters.data.CustomerData;
import com.lachonete.gerenciadorpedidos.adapters.repositories.CustomerRepository;
import com.lachonete.gerenciadorpedidos.entities.Customer;
import com.lachonete.gerenciadorpedidos.entities.valueobject.Cpf;
import com.lachonete.gerenciadorpedidos.entities.valueobject.CustomerId;
import com.lachonete.gerenciadorpedidos.entities.valueobject.EmailAddress;
import com.lachonete.gerenciadorpedidos.entities.valueobject.PersonName;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JpaCustomerGatewayTest {

    private CustomerRepository customerRepository;
    private JpaCustomerGateway jpaCustomerGateway;

    @BeforeEach
    void setUp() {
        customerRepository = mock(CustomerRepository.class);
        jpaCustomerGateway = new JpaCustomerGateway(customerRepository);
    }

    private CustomerData createCustomerData(UUID id, String cpf, String name, String email) {
        CustomerData customerData = new CustomerData();
        customerData.setId(id);
        customerData.setCpf(cpf);
        customerData.setName(name);
        customerData.setEmail(email);
        return customerData;
    }

    private Customer createCustomer(UUID id, String cpf, String name, String email) {
        return new Customer(
                new CustomerId(id),
                new Cpf(cpf),
                new EmailAddress(email),
                new PersonName(name)
        );
    }

    @Test
    void getAll_ShouldReturnListOfCustomersWhenRepositoryHasData() {
        // Given
        UUID id1 = UUID.randomUUID();
        UUID id2 = UUID.randomUUID();
        CustomerData data1 = createCustomerData(id1, "111.111.111-11", "Customer One", "one@example.com");
        CustomerData data2 = createCustomerData(id2, "222.222.222-22", "Customer Two", "two@example.com");
        List<CustomerData> customerDataList = Arrays.asList(data1, data2);

        when(customerRepository.findAll()).thenReturn(customerDataList);

        // When
        List<Customer> result = jpaCustomerGateway.getAll();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(id1, result.get(0).getId().getValue());
        assertEquals("111.111.111-11", result.get(0).getCpf().toString());
        assertEquals(id2, result.get(1).getId().getValue());
        assertEquals("222.222.222-22", result.get(1).getCpf().toString());

        verify(customerRepository, times(1)).findAll();
    }

    @Test
    void getAll_ShouldReturnEmptyListWhenRepositoryIsEmpty() {
        // Given
        when(customerRepository.findAll()).thenReturn(Collections.emptyList());

        // When
        List<Customer> result = jpaCustomerGateway.getAll();

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(customerRepository, times(1)).findAll();
    }

    @Test
    void add_ShouldSaveCustomerDataAndReturnNewCustomerId() {
        // Given
        Customer customerToAdd = createCustomer(null, "333.333.333-33", "New Customer", "new@example.com");

        // Use ArgumentCaptor to capture the CustomerData saved
        ArgumentCaptor<CustomerData> customerDataCaptor = ArgumentCaptor.forClass(CustomerData.class);
        when(customerRepository.save(customerDataCaptor.capture())).thenReturn(null); // Return null, as save might not return the entity

        // When
        CustomerId returnedId = jpaCustomerGateway.add(customerToAdd);

        // Then
        assertNotNull(returnedId);
        assertNotNull(returnedId.getValue()); // ID should be generated

        CustomerData capturedCustomerData = customerDataCaptor.getValue();
        assertNotNull(capturedCustomerData);
        assertEquals(returnedId.getValue(), capturedCustomerData.getId()); // Generated ID should be set in CustomerData
        assertEquals(customerToAdd.getCpf().toString(), capturedCustomerData.getCpf());
        assertEquals(customerToAdd.getName().toString(), capturedCustomerData.getName());
        assertEquals(customerToAdd.getEmail().toString(), capturedCustomerData.getEmail());

        verify(customerRepository, times(1)).save(any(CustomerData.class));
    }

    @Test
    void getById_ShouldReturnCustomerWhenFound() {
        // Given
        UUID customerId = UUID.randomUUID();
        CustomerData foundData = createCustomerData(customerId, "444.444.444-44", "Found Customer", "found@example.com");

        when(customerRepository.findById(customerId)).thenReturn(Optional.of(foundData));

        // When
        Customer result = jpaCustomerGateway.getById(customerId);

        // Then
        assertNotNull(result);
        assertEquals(customerId, result.getId().getValue());
        assertEquals("444.444.444-44", result.getCpf().toString());
        assertEquals("Found Customer", result.getName().toString());
        assertEquals("found@example.com", result.getEmail().toString());
        verify(customerRepository, times(1)).findById(customerId);
    }

    @Test
    void getById_ShouldReturnNullWhenNotFound() {
        // Given
        UUID customerId = UUID.randomUUID();
        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        // When
        Customer result = jpaCustomerGateway.getById(customerId);

        // Then
        assertNull(result);
        verify(customerRepository, times(1)).findById(customerId);
    }

    @Test
    void delete_ShouldCallRepositoryDeleteById() {
        // Given
        UUID customerIdToDelete = UUID.randomUUID();

        // When
        jpaCustomerGateway.delete(customerIdToDelete);

        // Then
        verify(customerRepository, times(1)).deleteById(customerIdToDelete);
    }
}