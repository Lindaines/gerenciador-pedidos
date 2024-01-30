package com.lachonete.gerenciadorpedidos.api.endpoints.payment.get;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class GetPaymentStatusByIdRequest {
    private UUID id;
}
