package com.lachonete.gerenciadorpedidos.ports.usescases.payment.add;

import java.util.UUID;

public class NewPaymentResponse {
    private UUID id;

    public NewPaymentResponse(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
