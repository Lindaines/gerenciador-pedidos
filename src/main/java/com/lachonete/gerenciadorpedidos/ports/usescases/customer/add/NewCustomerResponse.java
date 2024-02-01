package com.lachonete.gerenciadorpedidos.ports.usescases.customer.add;

import java.util.UUID;

public class NewCustomerResponse {
    private UUID id;

    public NewCustomerResponse(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
