package com.lachonete.gerenciadorpedidos.ports.usescases.order.add;

import java.util.UUID;

public class CheckoutOrderResponse {
    private UUID id;
    private Integer pickupCode;
    private String paymentId;

    public CheckoutOrderResponse(UUID id, Integer pickupCode, String paymentId) {
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
    public String getPaymentId() {
        return paymentId;
    }
}
