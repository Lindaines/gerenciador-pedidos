package com.lachonete.gerenciadorpedidos.application.core.domain.valueobject;

import java.util.Objects;

public class PersonName {

    private String firstname;
    private String middlename;
    private String lastname;
    @Override
    public boolean equals(Object o) { // <7>
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonName that = (PersonName) o;
        return firstname.equals(that.firstname)
                && middlename.equals(that.middlename)
                && lastname.equals(that.lastname);
    }

    @Override
    public int hashCode() { // <8>
        return Objects.hash(firstname, middlename, lastname);
    }
}
