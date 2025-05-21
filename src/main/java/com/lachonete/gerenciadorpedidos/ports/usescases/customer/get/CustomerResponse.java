package com.lachonete.gerenciadorpedidos.ports.usescases.customer.get;

import com.lachonete.gerenciadorpedidos.entities.valueobject.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerResponse {
    private UUID id;
    private String name;
    private String cpf;
    private String email;


}
