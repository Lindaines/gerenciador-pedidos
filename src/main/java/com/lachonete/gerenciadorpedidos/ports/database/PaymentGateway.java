package com.lachonete.gerenciadorpedidos.ports.database;


import com.lachonete.gerenciadorpedidos.entities.Order;
import com.lachonete.gerenciadorpedidos.entities.Payment;
import com.lachonete.gerenciadorpedidos.entities.valueobject.PaymentStatus;

import java.util.List;
import java.util.UUID;

public interface PaymentGateway {
    List<Payment> getAll();
    UUID add(Payment payment);
    Payment getById(UUID id);
    void updateStatus(UUID id, PaymentStatus paymentStatus);

    public interface BadRequest {}
    public interface NotFound {}


    public class PaymentNotFoundException extends RuntimeException implements NotFound {
    }

}
