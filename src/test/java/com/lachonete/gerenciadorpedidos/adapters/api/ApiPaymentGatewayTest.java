package com.lachonete.gerenciadorpedidos.adapters.api;

import com.lachonete.gerenciadorpedidos.entities.Order;
import com.lachonete.gerenciadorpedidos.entities.Payment;
import com.lachonete.gerenciadorpedidos.entities.valueobject.Money;
import com.lachonete.gerenciadorpedidos.entities.valueobject.OrderId;
import com.lachonete.gerenciadorpedidos.infra.PaymentRequest;
import com.lachonete.gerenciadorpedidos.infra.PaymentResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.web.client.ResourceAccessException; // Example of a RestTemplate exception
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field; // For setting @Value field
import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ApiPaymentGatewayTest {

    private RestTemplate restTemplate;
    private ApiPaymentGateway apiPaymentGateway;

    // Store original environment variables to restore them after tests
    private String originalPaymentCompany;
    private String originalPaymentCnpj;
    private String originalPaymentCity;
    private String originalPaymentStore;

    private static final String TEST_PAYMENT_API_URL = "http://test-payment-api.com/pay";

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        restTemplate = mock(RestTemplate.class);
        apiPaymentGateway = new ApiPaymentGateway(restTemplate);

        // Manually set the @Value field using reflection for unit testing
        Field paymentServiceURLField = ApiPaymentGateway.class.getDeclaredField("paymentServiceURL");
        paymentServiceURLField.setAccessible(true);
        paymentServiceURLField.set(apiPaymentGateway, TEST_PAYMENT_API_URL);

        // Backup and set environment variables for PaymentRequest
        originalPaymentCompany = System.getenv("PAYMENT_COMPANY");
        originalPaymentCnpj = System.getenv("PAYMENT_CNPJ");
        originalPaymentCity = System.getenv("PAYMENT_CITY");
        originalPaymentStore = System.getenv("PAYMENT_STORE");

        // Use System.setProperty to simulate environment variables for PaymentRequest
        // This is a common workaround in unit tests, but be aware of its limitations
        // regarding actual OS environment variables vs Java system properties.
        // For a more robust solution, consider wrapping System.getenv() in a utility
        // class that can be mocked.
        System.setProperty("PAYMENT_COMPANY", "MockCompany");
        System.setProperty("PAYMENT_CNPJ", "11.222.333/0001-44");
        System.setProperty("PAYMENT_CITY", "MockCity");
        System.setProperty("PAYMENT_STORE", "MockStore");
    }

    @AfterEach
    void tearDown() {
        // Restore original environment variables
        if (originalPaymentCompany != null) {
            System.setProperty("PAYMENT_COMPANY", originalPaymentCompany);
        } else {
            System.clearProperty("PAYMENT_COMPANY");
        }
        if (originalPaymentCnpj != null) {
            System.setProperty("PAYMENT_CNPJ", originalPaymentCnpj);
        } else {
            System.clearProperty("PAYMENT_CNPJ");
        }
        if (originalPaymentCity != null) {
            System.setProperty("PAYMENT_CITY", originalPaymentCity);
        } else {
            System.clearProperty("PAYMENT_CITY");
        }
        if (originalPaymentStore != null) {
            System.setProperty("PAYMENT_STORE", originalPaymentStore);
        } else {
            System.clearProperty("PAYMENT_STORE");
        }
    }



    @Test
    void createPayment_ShouldThrowRuntimeExceptionOnRestTemplateException() {
        // Given
        UUID orderUuid = UUID.randomUUID();
        OrderId orderId = new OrderId(orderUuid);
        var orderAmount = new Money(new BigDecimal("99.99"));

        var order = Order.OrderBuilder.anOrder()
                .withId(orderId)
                .withPrice(orderAmount)
                .build();

        // Simulate an exception from RestTemplate (e.g., network error, 5xx server error)
        when(restTemplate.postForObject(
                eq(TEST_PAYMENT_API_URL),
                any(PaymentRequest.class),
                eq(PaymentResponse.class)
        )).thenThrow(new ResourceAccessException("Connection refused"));

        // When / Then
        RuntimeException thrownException = assertThrows(RuntimeException.class, () -> {
            apiPaymentGateway.createPayment(order);
        });

        // The original code throws a generic RuntimeException without a message.
        // If you want to convey the original cause, change:
        // throw new RuntimeException();
        // to:
        // throw new RuntimeException("Failed to create payment", exception);
        // Then you could assert the cause:
        // assertTrue(thrownException.getCause() instanceof ResourceAccessException);
        assertNull(thrownException.getMessage(), "RuntimeException should not have a message as per current implementation.");


        verify(restTemplate, times(1)).postForObject(
                eq(TEST_PAYMENT_API_URL),
                any(PaymentRequest.class),
                eq(PaymentResponse.class)
        );
    }

    @Test
    void createPayment_ShouldHandleNullResponseFromRestTemplate() {
        // Given
        UUID orderUuid = UUID.randomUUID();
        OrderId orderId = new OrderId(orderUuid);
        var orderAmount = new Money(new BigDecimal("99.99"));

        var order = Order.OrderBuilder.anOrder()
                .withId(orderId)
                .withPrice(orderAmount)
                .build();

        // Simulate RestTemplate returning null
        when(restTemplate.postForObject(
                eq(TEST_PAYMENT_API_URL),
                any(PaymentRequest.class),
                eq(PaymentResponse.class)
        )).thenReturn(null);

        // When / Then
        // The original code will call response.toPayment(response) on a null response,
        // leading to a NullPointerException. This tests that behavior.
        assertThrows(RuntimeException.class, () -> {
            apiPaymentGateway.createPayment(order);
        }, "Should throw NullPointerException if RestTemplate returns null response.");

        verify(restTemplate, times(1)).postForObject(
                eq(TEST_PAYMENT_API_URL),
                any(PaymentRequest.class),
                eq(PaymentResponse.class)
        );
    }
}