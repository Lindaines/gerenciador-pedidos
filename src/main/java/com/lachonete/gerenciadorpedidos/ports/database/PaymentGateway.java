package com.lachonete.gerenciadorpedidos.ports.database;


import com.lachonete.gerenciadorpedidos.entities.PaymentDeprecated;
import com.lachonete.gerenciadorpedidos.entities.valueobject.PaymentStatus;

import java.util.List;
import java.util.UUID;

public interface PaymentGateway {
    List<PaymentDeprecated> getAll();
    UUID add(PaymentDeprecated payment);
    PaymentDeprecated getById(UUID id);
    void updateStatus(UUID id, PaymentStatus paymentStatus);

    public interface BadRequest {}
    public interface NotFound {}


    public class PaymentNotFoundException extends RuntimeException implements NotFound {
    }

}
