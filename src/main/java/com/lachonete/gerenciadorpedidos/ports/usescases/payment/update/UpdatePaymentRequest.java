package com.lachonete.gerenciadorpedidos.ports.usescases.payment.update;

import com.lachonete.gerenciadorpedidos.entities.valueobject.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePaymentRequest {
    private UUID id;
    private PaymentStatus status;
    }

