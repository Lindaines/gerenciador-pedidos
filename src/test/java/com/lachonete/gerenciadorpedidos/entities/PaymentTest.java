package com.lachonete.gerenciadorpedidos.entities;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class PaymentTest {
    // Constant test values
    private static final String VALID_PAYMENT_ID = "pay_123456789";
    private static final String OTHER_PAYMENT_ID = "pay_987654321";

    @Test
    void shouldCreatePaymentWithValidId() {
        Payment payment = new Payment(VALID_PAYMENT_ID);

        assertAll(
                () -> assertThat(payment.getPaymentId()).isEqualTo(VALID_PAYMENT_ID),
                () -> assertThat(payment.getPaymentId()).isNotBlank()
        );
    }

    @ParameterizedTest
    @ValueSource(strings = {
            VALID_PAYMENT_ID,
            OTHER_PAYMENT_ID,
            "PAY_ABC123",
            "p_1"
    })
    void shouldAcceptVariousValidPaymentIds(String validId) {
        Payment payment = new Payment(validId);
        assertEquals(validId, payment.getPaymentId());
    }

    @ParameterizedTest
    @NullAndEmptySource
    void shouldRejectNullOrEmptyPaymentId(String invalidId) {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Payment(invalidId),
                "Payment ID should not be null or empty"
        );
    }

    @Test
    void equalsAndHashCode_ShouldBeBasedOnPaymentId() {
        Payment payment1 = new Payment(VALID_PAYMENT_ID);
        Payment payment2 = new Payment(VALID_PAYMENT_ID);
        Payment differentPayment = new Payment(OTHER_PAYMENT_ID);

        assertAll(
                () -> assertThat(payment1).isEqualTo(payment2),
                () -> assertThat(payment1).isNotEqualTo(differentPayment),
                () -> assertThat(payment1.hashCode()).isEqualTo(payment2.hashCode())
        );
    }

    @Test
    void toString_ShouldContainPaymentId() {
        Payment payment = new Payment(VALID_PAYMENT_ID);
        assertThat(payment.toString()).contains(VALID_PAYMENT_ID);
    }
}