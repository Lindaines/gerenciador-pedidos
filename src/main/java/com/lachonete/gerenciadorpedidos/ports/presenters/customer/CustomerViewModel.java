package com.lachonete.gerenciadorpedidos.ports.presenters.customer;

import com.lachonete.gerenciadorpedidos.entities.valueobject.ProductCategory;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Builder
@Getter
public class CustomerViewModel {
    private UUID id;
    private String cpf;
    private String name;
    private String email;
}
