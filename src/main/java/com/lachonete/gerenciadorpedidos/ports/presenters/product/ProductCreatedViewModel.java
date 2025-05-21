package com.lachonete.gerenciadorpedidos.ports.presenters.product;

import lombok.Builder;

@Builder
public class ProductCreatedViewModel {

    private String id;

    public ProductCreatedViewModel(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
