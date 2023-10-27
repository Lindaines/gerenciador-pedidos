package com.lachonete.gerenciadorpedidos.application.ports.in.customer;

import com.lachonete.gerenciadorpedidos.application.core.domain.entity.Customer;

public interface CreateCustomerInputPort {
    void create(Customer customer);
}
