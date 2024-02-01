package com.lachonete.gerenciadorpedidos.ports.usescases.product.remove;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class RemoveProductRequest {
    private UUID id;

}
