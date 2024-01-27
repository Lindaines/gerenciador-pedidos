package com.lachonete.gerenciadorpedidos.ports.usescases.AddProduct;

import lombok.Getter;

import java.util.UUID;

@Getter
public class NewProductResponse {
    private UUID id;

    public NewProductResponse(UUID id) {
        this.id = id;
    }
}
