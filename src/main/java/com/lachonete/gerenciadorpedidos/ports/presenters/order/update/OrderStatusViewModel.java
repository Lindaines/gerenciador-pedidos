package com.lachonete.gerenciadorpedidos.ports.presenters.order.update;

import com.lachonete.gerenciadorpedidos.entities.valueobject.PaymentStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class OrderStatusViewModel {
    private UUID id;
    private PaymentStatus status;
}
