package com.lachonete.gerenciadorpedidos.entities;

import com.lachonete.gerenciadorpedidos.entities.valueobject.*;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class CustomerTest {

    // Test data
    private final CustomerId testId = new CustomerId(UUID.randomUUID());
    private final Cpf testCpf = new Cpf("123.456.789-09");
    private final EmailAddress testEmail = new EmailAddress("customer@test.com");
    private final PersonName testName = new PersonName("John Doe");

    @Test
    void shouldCreateCustomerWithRequiredFields() {
        Customer customer = new Customer(testCpf, testEmail, testName);

        assertAll(
                () -> assertThat(customer.getCpf()).isEqualTo(testCpf),
                () -> assertThat(customer.getEmail()).isEqualTo(testEmail),
                () -> assertThat(customer.getName()).isEqualTo(testName),
                () -> assertThat(customer.getId()).isNull()
        );
    }

    @Test
    void shouldCreateCustomerWithAllFieldsIncludingId() {
        Customer customer = new Customer(testId, testCpf, testEmail, testName);

        assertAll(
                () -> assertThat(customer.getId()).isEqualTo(testId),
                () -> assertThat(customer.getCpf()).isEqualTo(testCpf),
                () -> assertThat(customer.getEmail()).isEqualTo(testEmail),
                () -> assertThat(customer.getName()).isEqualTo(testName)
        );
    }

    @Test
    void equals_ShouldReturnTrue_WhenSameInstance() {
        Customer customer = new Customer(testId, testCpf, testEmail, testName);
        assertThat(customer.equals(customer)).isTrue();
    }

    @Test
    void equals_ShouldReturnFalse_WhenComparingWithNull() {
        Customer customer = new Customer(testId, testCpf, testEmail, testName);
        assertThat(customer.equals(null)).isFalse();
    }

    @Test
    void equals_ShouldReturnFalse_WhenDifferentClass() {
        Customer customer = new Customer(testId, testCpf, testEmail, testName);
        assertThat(customer.equals(new Object())).isFalse();
    }

    @Test
    void equals_ShouldReturnTrue_WhenIdsAreEqual() {
        Customer customer1 = new Customer(testId, testCpf, testEmail, testName);
        Customer customer2 = new Customer(testId,
                new Cpf("987.654.321-00"),
                new EmailAddress("other@email.com"),
                new PersonName("Jane Smith"));

        assertThat(customer1.equals(customer2)).isTrue();
    }

    @Test
    void hashCode_ShouldBeEqual_WhenIdsAreEqual() {
        Customer customer1 = new Customer(testId, testCpf, testEmail, testName);
        Customer customer2 = new Customer(testId,
                new Cpf("111.222.333-44"),
                new EmailAddress("different@email.com"),
                new PersonName("Different Name"));

        assertThat(customer1.hashCode()).isEqualTo(customer2.hashCode());
    }

}