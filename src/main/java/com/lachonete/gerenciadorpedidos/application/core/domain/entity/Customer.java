package com.lachonete.gerenciadorpedidos.application.core.domain.entity;

import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.Cpf;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.CustomerId;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.EmailAddress;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.PersonName;

import java.util.UUID;

public class Customer extends BaseEntity<CustomerId> {
    private Cpf cpf;

    public Customer(Cpf cpf, EmailAddress email, PersonName name) {
        this.cpf = cpf;
        this.email = email;
        this.name = name;
    }
    public Customer( CustomerId id, Cpf cpf, EmailAddress email, PersonName name) {
        super.setId(id);
        this.cpf = cpf;
        this.email = email;
        this.name = name;
    }
    public Cpf getCpf() {
        return cpf;
    }

    public EmailAddress getEmail() {
        return email;
    }

    public PersonName getName() {
        return name;
    }

    private EmailAddress email;
    private PersonName name;

}
