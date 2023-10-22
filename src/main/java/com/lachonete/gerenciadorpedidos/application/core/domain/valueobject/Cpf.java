package com.lachonete.gerenciadorpedidos.application.core.domain.valueobject;

public class Cpf {
    private String value;

    public Cpf(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

// to do: implement CPF validations
}
