package com.lachonete.gerenciadorpedidos.application.core.domain.entity;

import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.CPF;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.CustomerId;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.EmailAddress;
import com.lachonete.gerenciadorpedidos.application.core.domain.valueobject.PersonName;

public class Customer extends BaseEntity<CustomerId> {
    private CPF cpf;
    private EmailAddress email;
    private PersonName name;

}
