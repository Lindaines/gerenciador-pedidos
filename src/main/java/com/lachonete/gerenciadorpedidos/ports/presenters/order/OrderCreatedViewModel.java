package com.lachonete.gerenciadorpedidos.ports.presenters.order;

import java.util.UUID;

public class OrderCreatedViewModel {

    private String id;
    private Integer pickupCode;
    private UUID paymentId;

    public OrderCreatedViewModel(String id, Integer pickupCode, UUID paymentId) {
        this.id = id;
        this.pickupCode = pickupCode;
        this.paymentId = paymentId;
    }

    public String getId() {
        return id;
    }
    public Integer getPickupCode() {
        return pickupCode;
    }
    public UUID getPaymentId() {
        return paymentId;
    }

}
