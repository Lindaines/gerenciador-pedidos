package com.lachonete.gerenciadorpedidos.adapters.out.repository.entity;

//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "customers")
public class CustomerEntity {
    @Id
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    private String cpf;

    public UUID getId() {
        return id;
    }

    public String getCpf() {
        return cpf;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    private String name;
    private String email;

    public CustomerEntity(String cpf, String name, String email) {
        this.cpf = cpf;
        this.name = name;
        this.email = email;
    }
}
