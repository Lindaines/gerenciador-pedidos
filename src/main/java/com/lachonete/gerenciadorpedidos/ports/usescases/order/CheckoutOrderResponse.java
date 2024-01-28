package com.lachonete.gerenciadorpedidos.ports.usescases.order;

import java.util.UUID;

public class CheckoutOrderResponse {
    private UUID id;
    private Integer pickupCode;

    public CheckoutOrderResponse(UUID id, Integer pickupCode) {
        this.id = id;
        this.pickupCode = pickupCode;
    }

    public UUID getId() {
        return id;
    }
    public Integer getPickupCode() {
        return pickupCode;
    }
}
