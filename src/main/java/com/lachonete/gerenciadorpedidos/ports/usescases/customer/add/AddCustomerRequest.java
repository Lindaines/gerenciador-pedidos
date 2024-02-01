package com.lachonete.gerenciadorpedidos.ports.usescases.customer.add;

import com.lachonete.gerenciadorpedidos.entities.valueobject.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddCustomerRequest {
        private String name;
        private String cpf;
        private String email;
    }

