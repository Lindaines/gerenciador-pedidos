package com.lachonete.gerenciadorpedidos.ports.presenters.order;

public class OrderCreatedViewModel {

    private String id;
    private Integer pickupCode;

    public OrderCreatedViewModel(String id, Integer pickupCode) {
        this.id = id;
        this.pickupCode = pickupCode;
    }

    public String getId() {
        return id;
    }
    public Integer getPickupCode() {
        return pickupCode;
    }

}
