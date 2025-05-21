package com.lachonete.gerenciadorpedidos.usecases.exception;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PaymentServiceExceptionTest {

    @Test
    void shouldCreateExceptionWithMessage() {
        // Given
        String errorMessage = "Payment failed due to invalid data.";

        // When
        PaymentServiceException exception = new PaymentServiceException(errorMessage);

        // Then
        assertNotNull(exception);
        assertEquals(errorMessage, exception.getMessage(), "The exception message should match the one provided.");
        assertNull(exception.getCause(), "The exception should not have a cause when only a message is provided.");
    }

    @Test
    void shouldCreateExceptionWithMessageAndCause() {
        // Given
        String errorMessage = "Payment system is unreachable.";
        Throwable cause = new RuntimeException("Network error: Connection timed out.");

        // When
        PaymentServiceException exception = new PaymentServiceException(errorMessage, cause);

        // Then
        assertNotNull(exception);
        assertEquals(errorMessage, exception.getMessage(), "The exception message should match the one provided.");
        assertNotNull(exception.getCause(), "The exception should have a cause.");
        assertEquals(cause, exception.getCause(), "The exception cause should be the one provided.");
        assertEquals("Network error: Connection timed out.", exception.getCause().getMessage(), "The cause message should be correct.");
        assertTrue(exception.getCause() instanceof RuntimeException, "The cause should be an instance of RuntimeException.");
    }

    @Test
    void shouldBeRuntimeExceptionInstance() {
        // Given
        String errorMessage = "Generic payment error.";

        // When
        PaymentServiceException exception = new PaymentServiceException(errorMessage);

        // Then
        assertTrue(exception instanceof RuntimeException, "PaymentServiceException should be an instance of RuntimeException.");
    }
}