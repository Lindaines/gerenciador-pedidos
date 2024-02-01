package com.lachonete.gerenciadorpedidos.api.endpoints.payment.update;

import com.lachonete.gerenciadorpedidos.entities.valueobject.PaymentStatus;
import lombok.Data;

@Data
public class UpdatePaymentStatusRequest {
    private PaymentStatus paymentStatus;
}
