package com.lachonete.gerenciadorpedidos.ports.usescases.payment.get;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class GetPaymentByIdRequest {
    private UUID id;

}
