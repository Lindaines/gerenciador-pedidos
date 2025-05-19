package com.lachonete.gerenciadorpedidos.ports.presenters.payment;

import com.lachonete.gerenciadorpedidos.entities.valueobject.PaymentStatus;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
@Getter
public class PaymentStatusViewModel {
    private String id;
    private PaymentStatus status;
}
