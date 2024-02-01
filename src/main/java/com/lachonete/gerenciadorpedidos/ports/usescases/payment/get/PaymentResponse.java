package com.lachonete.gerenciadorpedidos.ports.usescases.payment.get;

import com.lachonete.gerenciadorpedidos.entities.valueobject.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentResponse {
    private UUID id;
    private PaymentStatus paymentStatus;
    private UUID orderId;
    private UUID customerId;
    private BigDecimal price;
}
