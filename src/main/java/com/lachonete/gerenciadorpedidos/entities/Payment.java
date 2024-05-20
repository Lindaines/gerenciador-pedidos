package com.lachonete.gerenciadorpedidos.entities;

import com.lachonete.gerenciadorpedidos.entities.valueobject.CustomerId;
import com.lachonete.gerenciadorpedidos.entities.valueobject.Money;
import com.lachonete.gerenciadorpedidos.entities.valueobject.OrderId;
import com.lachonete.gerenciadorpedidos.entities.valueobject.PaymentStatus;

import java.util.UUID;

public class Payment {
    public String getPaymentId() {
        return paymentId;
    }

    private String paymentId;

    public Payment(String paymentId) {
        this.paymentId = paymentId;
    }
}
