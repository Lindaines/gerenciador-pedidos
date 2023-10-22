package com.lachonete.gerenciadorpedidos.application.ports.in;

import com.lachonete.gerenciadorpedidos.application.core.domain.entity.Customer;

public interface CreateCustomerInputPort {
    void create(Customer customer);
}
