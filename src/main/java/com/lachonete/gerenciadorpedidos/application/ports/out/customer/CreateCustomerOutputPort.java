package com.lachonete.gerenciadorpedidos.application.ports.out.customer;

import com.lachonete.gerenciadorpedidos.application.core.domain.entity.Customer;

public interface CreateCustomerOutputPort {
    void insert (Customer customer);
}
