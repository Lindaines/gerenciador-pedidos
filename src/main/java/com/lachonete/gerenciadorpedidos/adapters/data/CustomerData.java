package com.lachonete.gerenciadorpedidos.adapters.data;

import com.lachonete.gerenciadorpedidos.entities.valueobject.ProductCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "customers")
public class CustomerData {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    private String cpf;

    private String name;
    private String email;

    public CustomerData(String cpf, String name, String email) {
        this.cpf = cpf;
        this.name = name;
        this.email = email;
    }
}
