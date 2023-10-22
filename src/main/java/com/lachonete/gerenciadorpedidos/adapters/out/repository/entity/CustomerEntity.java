package com.lachonete.gerenciadorpedidos.adapters.out.repository.entity;

import jakarta.persistence.Entity;
import lombok.*;
import org.springframework.data.annotation.Id;


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

    public CustomerEntity(UUID id, String cpf, String name, String email) {
        this.id = id;
        this.cpf = cpf;
        this.name = name;
        this.email = email;
    }
}
