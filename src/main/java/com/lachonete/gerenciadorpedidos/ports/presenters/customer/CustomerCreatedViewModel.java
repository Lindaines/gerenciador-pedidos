package com.lachonete.gerenciadorpedidos.ports.presenters.customer;

public class CustomerCreatedViewModel {

    private String id;

    public CustomerCreatedViewModel(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
