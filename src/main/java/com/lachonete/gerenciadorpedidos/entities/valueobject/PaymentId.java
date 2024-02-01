package com.lachonete.gerenciadorpedidos.entities.valueobject;

import java.util.UUID;

public class PaymentId extends BaseId<UUID> {
    public PaymentId(UUID value) {
        super(value);
    }
}
