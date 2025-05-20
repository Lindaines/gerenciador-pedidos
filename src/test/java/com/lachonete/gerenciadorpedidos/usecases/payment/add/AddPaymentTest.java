package com.lachonete.gerenciadorpedidos.usecases.payment.add;

import com.lachonete.gerenciadorpedidos.entities.Order;
import com.lachonete.gerenciadorpedidos.entities.Payment;
import com.lachonete.gerenciadorpedidos.entities.valueobject.Money;
import com.lachonete.gerenciadorpedidos.entities.valueobject.OrderId;
import com.lachonete.gerenciadorpedidos.entities.valueobject.OrderStatus;
import com.lachonete.gerenciadorpedidos.ports.api.PaymentGateway;
import com.lachonete.gerenciadorpedidos.ports.usescases.payment.add.AddPaymentRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddPaymentTest {

    @Mock
    private PaymentGateway paymentGateway;

    private AddPayment addPayment;

    @BeforeEach
    void setUp() {
        addPayment = new AddPayment(paymentGateway);
    }

    @Test
    void execute_shouldCreatePaymentAndReturnPaymentId() {
        var paymentId = "682a97472000155b9f41fc7b";
        // Given
        var order = Order.OrderBuilder.anOrder()
                .withId(new OrderId(UUID.fromString("d45b6c71-c7d7-4a04-bf92-190df9612ff1")))
                .withPickupCode(1001)
                .withOrderStatus(OrderStatus.EM_PREPARACAO)
                .withPrice(new Money(new BigDecimal("26.00")))
                .withItems(List.of())
                .build();        String expectedPaymentId = "682a97472000155b9f41fc7b";

        AddPaymentRequest request = new AddPaymentRequest(order);

        Payment mockPayment = new Payment(paymentId);

        when(paymentGateway.createPayment(order)).thenReturn(mockPayment);

        // When
        String actualPaymentId = addPayment.execute(request);

        // Then
        assertEquals(expectedPaymentId, actualPaymentId);
        verify(paymentGateway).createPayment(order);
    }

    @Test
    void execute_withNullRequest_shouldThrowNullPointerException() {
        assertThrows(NullPointerException.class, () -> addPayment.execute(null));
    }

    @Test
    void execute_withNullOrderId_shouldThrowNullPointerException() {
        AddPaymentRequest request = new AddPaymentRequest(null);
        assertThrows(NullPointerException.class, () -> addPayment.execute(request));
    }

}