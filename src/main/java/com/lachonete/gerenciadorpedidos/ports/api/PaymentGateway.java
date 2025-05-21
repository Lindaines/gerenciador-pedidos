package com.lachonete.gerenciadorpedidos.ports.api;

import com.lachonete.gerenciadorpedidos.entities.Order;
import com.lachonete.gerenciadorpedidos.entities.Payment;

public interface PaymentGateway {
    Payment createPayment(Order order);
}
