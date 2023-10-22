package com.lachonete.gerenciadorpedidos.application.core.domain.valueobject;

import java.util.Objects;

public class PersonName {

    private String name;

    // to do: implement domain validations


    public PersonName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
