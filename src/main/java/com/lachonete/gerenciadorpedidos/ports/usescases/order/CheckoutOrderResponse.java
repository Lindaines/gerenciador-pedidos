package com.lachonete.gerenciadorpedidos.ports.usescases.order;

import java.util.UUID;

public class CheckoutOrderResponse {
    private UUID id;
    private Integer pickupCode;
    private UUID paymentId;

    public CheckoutOrderResponse(UUID id, Integer pickupCode, UUID paymentId) {
        this.id = id;
        this.pickupCode = pickupCode;
        this.paymentId = paymentId;
    }

    public UUID getId() {
        return id;
    }
    public Integer getPickupCode() {
        return pickupCode;
    }
    public UUID getPaymentId() {
        return paymentId;
    }
}
