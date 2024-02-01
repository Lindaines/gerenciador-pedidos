package com.lachonete.gerenciadorpedidos.ports.usescases.product.add;

import java.util.UUID;

public class NewProductResponse {
    private UUID id;

    public NewProductResponse(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }
}
