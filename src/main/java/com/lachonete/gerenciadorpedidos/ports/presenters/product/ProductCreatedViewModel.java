package com.lachonete.gerenciadorpedidos.ports.presenters.product;

public class ProductCreatedViewModel {

    private String id;

    public ProductCreatedViewModel(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
