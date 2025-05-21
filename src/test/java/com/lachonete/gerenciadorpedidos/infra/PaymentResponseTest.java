package com.lachonete.gerenciadorpedidos.infra;

import com.lachonete.gerenciadorpedidos.entities.Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field; // Required for setting private fields

import static org.junit.jupiter.api.Assertions.*;

class PaymentResponseTest {

    private PaymentResponse paymentResponse;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        // We need to use reflection to set private fields for testing,
        // as there's no public constructor or setters in PaymentResponse.
        // In a real project, consider adding a builder or all-args constructor (e.g., via Lombok).
        paymentResponse = new PaymentResponse();

        // Set values for the private fields using reflection
        setPrivateField(paymentResponse, "payment_id", "pay_123abc");
        setPrivateField(paymentResponse, "pix_qr_code_url", "https://example.com/qr/123");
        setPrivateField(paymentResponse, "status", "APPROVED");
    }

    // Helper method to set private fields using reflection
    private void setPrivateField(Object target, String fieldName, Object value) throws NoSuchFieldException, IllegalAccessException {
        Field field = target.getClass().getDeclaredField(fieldName);
        field.setAccessible(true); // Allow access to private field
        field.set(target, value);
    }

    @Test
    void getPayment_id_ShouldReturnCorrectId() {
        // When
        String result = paymentResponse.getPayment_id();

        // Then
        assertEquals("pay_123abc", result);
    }

    @Test
    void getPix_qr_code_url_ShouldReturnCorrectUrl() {
        // When
        String result = paymentResponse.getPix_qr_code_url();

        // Then
        assertEquals("https://example.com/qr/123", result);
    }

    @Test
    void getStatus_ShouldReturnCorrectStatus() {
        // When
        String result = paymentResponse.getStatus();

        // Then
        assertEquals("APPROVED", result);
    }

    @Test
    void toPayment_ShouldCreatePaymentWithCorrectId() throws NoSuchFieldException, IllegalAccessException {
        // Given: paymentResponse is set up in @BeforeEach with payment_id = "pay_123abc"
        // Also test with a different payment_id to be sure
        PaymentResponse newPaymentResponse = new PaymentResponse();
        setPrivateField(newPaymentResponse, "payment_id", "pay_456def");
        setPrivateField(newPaymentResponse, "pix_qr_code_url", "url");
        setPrivateField(newPaymentResponse, "status", "PENDING");


        // When
        Payment payment = newPaymentResponse.toPayment(newPaymentResponse);

        // Then
        assertNotNull(payment);
        assertEquals("pay_456def", payment.getPaymentId());
    }


}