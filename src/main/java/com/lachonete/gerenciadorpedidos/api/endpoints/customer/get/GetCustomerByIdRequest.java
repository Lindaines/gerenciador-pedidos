package com.lachonete.gerenciadorpedidos.api.endpoints.customer.get;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class GetCustomerByIdRequest {
    private UUID id;
}
