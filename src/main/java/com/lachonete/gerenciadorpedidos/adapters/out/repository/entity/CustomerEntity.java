package com.lachonete.gerenciadorpedidos.adapters.out.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;


import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CustomerEntity {
    @Id
    private UUID id;
    private String cpf;
    private String name;
    private String email;

    public CustomerEntity(String cpf, String name, String email) {
        this.cpf = cpf;
        this.name = name;
        this.email = email;
    }
}
