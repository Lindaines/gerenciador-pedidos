package com.lachonete.gerenciadorpedidos.ports.usescases.payment.add;

import com.lachonete.gerenciadorpedidos.entities.valueobject.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddPaymentRequest {
    private UUID orderId;
    private UUID customerId;
    private BigDecimal price;
    }

