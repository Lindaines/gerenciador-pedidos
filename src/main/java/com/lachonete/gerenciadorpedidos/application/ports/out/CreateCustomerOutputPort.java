package com.lachonete.gerenciadorpedidos.application.ports.out;

import com.lachonete.gerenciadorpedidos.application.core.domain.entity.Customer;

public interface CreateCustomerOutputPort {
    void insert (Customer customer);
}
