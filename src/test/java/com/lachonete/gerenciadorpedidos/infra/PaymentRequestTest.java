package com.lachonete.gerenciadorpedidos.infra;

import com.lachonete.gerenciadorpedidos.entities.Order;
import com.lachonete.gerenciadorpedidos.entities.valueobject.OrderId;
import com.lachonete.gerenciadorpedidos.entities.valueobject.Money;
import com.lachonete.gerenciadorpedidos.entities.valueobject.OrderStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRequestTest {

    // Store original environment variables to restore them after tests
    private String originalPaymentCompany;
    private String originalPaymentCnpj;
    private String originalPaymentCity;
    private String originalPaymentStore;

    @BeforeEach
    void setUp() {
        // Backup original environment variables
        originalPaymentCompany = System.getenv("PAYMENT_COMPANY");
        originalPaymentCnpj = System.getenv("PAYMENT_CNPJ");
        originalPaymentCity = System.getenv("PAYMENT_CITY");
        originalPaymentStore = System.getenv("PAYMENT_STORE");

        // Set test environment variables
        System.setProperty("PAYMENT_COMPANY", "TestCompany");
        System.setProperty("PAYMENT_CNPJ", "12.345.678/0001-90");
        System.setProperty("PAYMENT_CITY", "TestCity");
        System.setProperty("PAYMENT_STORE", "TestStore");
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
    void constructor_ShouldHandleNullOrderAndMoneyGracefully_OrThrowAppropriateException() {
        // Given: An order with null ID or null Money
        // Note: In a real application, the Order constructor might prevent this,
        // but for testing the PaymentRequest's resilience to invalid Order state.

        // Test with null OrderId.value
        Order orderWithNullOrderIdValue = Order.OrderBuilder.anOrder()
                .withId(null)
                .withPrice(null)
                .withPaymentId("123123")
                .withPickupCode(1001)
                .withOrderStatus(OrderStatus.RECEBIDO)
                .build();

        // When/Then: Expecting a NullPointerException when calling order.getId().getValue().toString()
        assertThrows(NullPointerException.class, () -> new PaymentRequest(orderWithNullOrderIdValue),
                "Should throw NullPointerException if OrderId.value is null when toString() is called.");

        // Test with null OrderId
        Order orderWithNullOrderId = Order.OrderBuilder.anOrder()
                .withId(null)
                .withPrice(null)
                .withPaymentId("123123")
                .withPickupCode(1001)
                .withOrderStatus(OrderStatus.RECEBIDO)
                .build();
        // When/Then: Expecting a NullPointerException when calling order.getId()
        assertThrows(NullPointerException.class, () -> new PaymentRequest(orderWithNullOrderId),
                "Should throw NullPointerException if OrderId is null when getId() is called.");


        // Test with null Money
        Order orderWithNullMoney = Order.OrderBuilder.anOrder()
                .withId(new OrderId(UUID.randomUUID()))
                .withPrice(null)
                .withPaymentId("123123")
                .withPickupCode(1001)
                .withOrderStatus(OrderStatus.RECEBIDO)
                .build();

        // When/Then: Expecting a NullPointerException when calling order.getMoney()
        assertThrows(NullPointerException.class, () -> new PaymentRequest(orderWithNullMoney),
                "Should throw NullPointerException if Money is null when getMoney() is called.");


        // Test with null Money.amount
        Order orderWithNullMoneyAmount = Order.OrderBuilder.anOrder()
                .withId(new OrderId(UUID.randomUUID()))
                .withPrice(null)
                .withPaymentId("123123")
                .withPickupCode(1001)
                .withOrderStatus(OrderStatus.RECEBIDO)
                .build();

        // When/Then: Expecting a NullPointerException when calling getAmount()
        assertThrows(NullPointerException.class, () -> new PaymentRequest(orderWithNullMoneyAmount),
                "Should throw NullPointerException if Money.amount is null when getAmount() is called.");
    }




}