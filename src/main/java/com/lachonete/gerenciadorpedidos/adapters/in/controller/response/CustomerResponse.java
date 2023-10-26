package com.lachonete.gerenciadorpedidos.adapters.in.controller.response;

import com.lachonete.gerenciadorpedidos.application.core.domain.entity.Customer;
import lombok.Data;

import java.util.UUID;

@Data
public class CustomerResponse {

    private UUID id;
    private String cpf;
    private String name;
    private String email;

    public CustomerResponse(Customer customer) {
        this.id = customer.getId().getValue();
        this.cpf = customer.getCpf().toString();
        ;
        this.name = customer.getEmail().toString();
        ;
        this.email = customer.getName().toString();
        ;
    }
}
